package com.legenkiy.note_api.service.api;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void register(UserDto userDto);
    User findById(Long id);
    User findByUsername(String username);
    void update(UserDto user, Long id);
    void delete(Long id);

}
