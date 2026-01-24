package com.legenkiy.note_api.controllers;

import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto){
        userService.register(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message", "User was registered"
                        )
                );
    }





}
