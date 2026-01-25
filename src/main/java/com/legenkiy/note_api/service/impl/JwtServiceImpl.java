package com.legenkiy.note_api.service.impl;

import com.legenkiy.note_api.model.RefreshToken;
import com.legenkiy.note_api.service.api.JwtService;
import com.legenkiy.note_api.service.api.RefreshTokenService;
import com.legenkiy.note_api.service.api.UserService;
import com.legenkiy.note_api.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;


@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtUtils.getJwtSecretKey().getBytes());
    }


    @Override
    public String generateJwtAccessToken(UserDetails userDetails) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(System.currentTimeMillis() + jwtUtils.getJwtAccessExpiration());
        return Jwts.builder()
                .signWith(getSigningKey())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .compact();
    }

    @Override
    public void generateJwtRefreshToken(UserDetails userDetails, HttpServletRequest httpServletRequest) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(System.currentTimeMillis() + jwtUtils.getJwtRefreshExpiration());
        String token =  Jwts.builder()
                .signWith(getSigningKey())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .compact();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserId(userService.findByUsername(userDetails.getUsername()));
        refreshToken.setUserAgent(httpServletRequest.getHeader("User-Agent"));
        refreshToken.setIp(httpServletRequest.getHeader("X-Forwarded-For"));
        refreshTokenService.save(refreshToken);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenNonExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (userDetails.getUsername().equals(extractUsername(token)) && isTokenNonExpired(token)){
            return true;
        }
        else {
            refreshTokenService.revoke(token);
            return false;
        }
    }

    @Override
    public String extractTokenFromCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) return null;
        Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("accessToken")).findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found in cookies"));
        return cookie.getValue();
    }




}
