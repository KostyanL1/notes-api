package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<User> user(Authentication authentication) {
        return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody UserDto userDto, Authentication authentication) {
        userService.update(userDto, id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body("User was updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, Authentication authentication) {
        userService.delete(id, authentication);
        return ResponseEntity.ok("User was deleted");
    }


}
