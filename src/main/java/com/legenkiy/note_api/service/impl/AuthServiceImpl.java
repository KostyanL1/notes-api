package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.AuthTokens;
import com.legenkiy.note_api.dto.UserDto;
import com.legenkiy.note_api.exceptions.ObjectNotFoundExceprion;
import com.legenkiy.note_api.model.RefreshToken;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final CookieService cookieService;
    private final UserDetailsService userDetailsService;


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
    public AuthTokens refresh(HttpServletRequest httpServletRequest) {
        String refreshToken = cookieService.extractTokenFromCookie("refreshToken", httpServletRequest);
        if (refreshToken == null) {
            throw new ObjectNotFoundExceprion("Token not found");
        }
        RefreshToken refreshTokenEntity = refreshTokenService.findByToken(refreshToken);
        User user = refreshTokenEntity.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (refreshTokenEntity.isRevoked() || !jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new JwtException("Invalid refresh token");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String accessToken = jwtService.generateJwtAccessToken(authentication);
        refreshTokenService.revoke(refreshToken);
        String newRefreshToken = jwtService.generateJwtRefreshToken(authentication);
        refreshTokenService.save(
                newRefreshToken,
                user,
                httpServletRequest.getHeader("User-Agent"),
                httpServletRequest.getRemoteAddr()
        );
        return new AuthTokens(newRefreshToken, accessToken);

    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        String refreshToken = cookieService.extractTokenFromCookie("refreshToken", httpServletRequest);
        if (refreshToken != null) {
            refreshTokenService.revoke(refreshToken);
        }

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
