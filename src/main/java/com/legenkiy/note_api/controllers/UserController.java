package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<User> userById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        userService.update(userDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("User was updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok("User was deleted");
    }


}
