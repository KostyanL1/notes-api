package com.legenkiy.note_api.service.impl;

import com.legenkiy.note_api.service.api.JwtService;
import com.legenkiy.note_api.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtUtils.getJwtSecretKey().getBytes());
    }


    @Override
    public String generateJwtAccessToken(Authentication authentication) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(System.currentTimeMillis() + jwtUtils.getJwtAccessExpiration());
        return Jwts.builder()
                .signWith(getSigningKey())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .compact();
    }

    @Override
    public String generateJwtRefreshToken(Authentication authentication) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(System.currentTimeMillis() + jwtUtils.getJwtRefreshExpiration());
        return Jwts.builder()
                .signWith(getSigningKey())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .compact();
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
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) && isTokenExpired(token);
    }






}
