package com.legenkiy.note_api.controllers;

import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.service.api.JwtService;
import com.legenkiy.note_api.service.api.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String accessToken = jwtService.generateJwtAccessToken(userDetailsService.loadUserByUsername(userDto.getUsername()));

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(15 * 60);
        accessTokenCookie.setAttribute("SameSite", "Strict");
        httpServletResponse.addCookie(accessTokenCookie);

        userService.register(userDto, httpServletRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message", "User was registered"
                        )
                );
    }








}
