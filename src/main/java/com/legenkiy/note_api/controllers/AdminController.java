package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor

public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> users(){
        return ResponseEntity.ok(userService.findAll());
    }


}
