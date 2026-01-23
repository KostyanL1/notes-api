package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.repository.UserRepository;
import com.legenkiy.note_api.service.api.NoteService;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }
    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!") );
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    @Transactional
    public void update(UserDto userDto, Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("Failed to update user!");
        }
    }

    @Override
    @Transactional
    public void delete(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Failed to delete user!");
        }
    }




}
