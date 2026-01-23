package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.UserService;
import com.legenkiy.note_api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<User>> users(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

}
