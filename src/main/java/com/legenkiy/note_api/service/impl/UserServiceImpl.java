package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.enums.Role;
import com.legenkiy.note_api.exceptions.ObjectNotFoundException;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.repository.UserRepository;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean isExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found!"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public void update(UserDto userDto, Long id, Authentication authentication) {
        User user = getUserIfAuthorized(id, authentication);
        user.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id, Authentication authentication) {
        User user = getUserIfAuthorized(id, authentication);
        userRepository.deleteById(user.getId());

    }


    private User getUserIfAuthorized(Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));
        User user;
        if (isAdmin) {
            user = userRepository.findById(id).orElseThrow(
                    () -> new ObjectNotFoundException("User not found")
            );
        } else {
            user = userRepository.findByIdAndUsername(id, authentication.getName()).orElseThrow(
                    () -> new AccessDeniedException("Forbidden")
            );
        }
        return user;
    }


}
