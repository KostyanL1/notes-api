package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.model.RefreshToken;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    String generateJwtAccessToken(UserDetails userDetails);
    void generateJwtRefreshToken(UserDetails userDetails, HttpServletRequest httpServletRequest);
    <T> T extractClaims(String token, Function<Claims, T> resolver);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenNonExpired(String token);
    String extractTokenFromCookie(HttpServletRequest httpServletRequest);

}
