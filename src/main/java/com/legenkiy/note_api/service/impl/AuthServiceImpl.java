package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.AuthTokens;
import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.AuthService;
import com.legenkiy.note_api.service.api.JwtService;
import com.legenkiy.note_api.service.api.RefreshTokenService;
import com.legenkiy.note_api.service.api.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;


    @Override
    public AuthTokens register(UserDto userDto, HttpServletRequest httpServletRequest) {
        userService.save(userDto);
        return authenticateAndIssueTokens(userDto.getUsername(), userDto.getPassword(), httpServletRequest);
    }


    @Override
    public AuthTokens login(UserDto userDto, HttpServletRequest httpServletRequest) {
        return authenticateAndIssueTokens(userDto.getUsername(), userDto.getPassword(), httpServletRequest);
    }

    @Override
    public AuthTokens refresh(String token, HttpServletRequest httpServletRequest) {
        return new AuthTokens();
    }

    @Override
    public void logout(UserDto userDto) {
        return new AuthTokens();
    }


    private AuthTokens authenticateAndIssueTokens(String username, String password, HttpServletRequest httpServletRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String realUsername = auth.getName();
        User user = userService.findByUsername(realUsername);
        String accessToken = jwtService.generateJwtAccessToken(auth);
        String refreshToken = jwtService.generateJwtRefreshToken(auth);
        refreshTokenService.save(
                refreshToken,
                user,
                httpServletRequest.getHeader("User-Agent"),
                httpServletRequest.getRemoteAddr()
        );
        return new AuthTokens(refreshToken, accessToken);
    }

}
