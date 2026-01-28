package com.legenkiy.note_api.service.api;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface UserService {

    boolean isExists(String username);

    List<User> findAll();

    User save(UserDto userDto);

    User findById(Long id);

    User findByUsername(String username);

    void update(UserDto user, Long id, Authentication authentication);

    void delete(Long id, Authentication authentication);

}
