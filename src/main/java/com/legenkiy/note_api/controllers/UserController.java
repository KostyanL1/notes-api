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
    public ResponseEntity<User> userById(@PathVariable("id") long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserDto userDto){
        userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User was created");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody UserDto userDto){
        userService.update(userDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("User was updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        userService.delete(id);
        return ResponseEntity.ok("User was deleted");
    }


}
