package com.legenkiy.note_api.service.impl;

import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.model.UserDetailsImpl;
import com.legenkiy.note_api.repository.UserRepository;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow( () -> new RuntimeException("User not found!"));
        return new UserDetailsImpl(user);
    }
}
