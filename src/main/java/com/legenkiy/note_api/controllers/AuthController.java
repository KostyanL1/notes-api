package com.legenkiy.note_api.controllers;

import com.legenkiy.note_api.dto.AuthTokens;
import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.service.api.AuthService;
import com.legenkiy.note_api.service.api.CookieService;
import com.legenkiy.note_api.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final CookieService cookieService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        AuthTokens authTokens = authService.register(userDto, httpServletRequest);
        setCookies(httpServletResponse, authTokens);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message", "user was registered"
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        AuthTokens authTokens = authService.login(userDto, httpServletRequest);
        setCookies(httpServletResponse, authTokens);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "message", "user successfully logged in."
                        )
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        AuthTokens authTokens = authService.refresh(httpServletRequest);
        setCookies(httpServletResponse, authTokens);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "message", "tokens refreshed"
                        )
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        authService.logout(httpServletRequest);
        cookieService.deleteCookie("accessToken", httpServletResponse);
        cookieService.deleteCookie("refreshToken", httpServletResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "message", "user successfully logged out"
                        )
                );
    }

    private void setCookies(HttpServletResponse httpServletResponse, AuthTokens authTokens) {
        Cookie cookieAccessToken = cookieService.createCookieWithToken("accessToken", authTokens.getAccess(),(int) Long.parseLong(jwtUtils.getJwtAccessExpiration()) / 1000);
        Cookie cookieRefreshToken = cookieService.createCookieWithToken("refreshToken", authTokens.getRefresh(), (int) Long.parseLong(jwtUtils.getJwtRefreshExpiration()) / 1000);
        httpServletResponse.addCookie(cookieAccessToken);
        httpServletResponse.addCookie(cookieRefreshToken);
    }



}
