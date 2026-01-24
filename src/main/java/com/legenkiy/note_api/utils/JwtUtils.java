package com.legenkiy.note_api.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretKey;
    @Value("${jwt.expiration.access}")
    private String jwtAccessExpiration;
    @Value("${jwt.expiration.refresh}")
    private String jwtRefreshExpiration;


}
