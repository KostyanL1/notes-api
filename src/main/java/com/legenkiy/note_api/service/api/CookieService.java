package com.legenkiy.note_api.service.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {

    Cookie createCookieWithAccessToken(String accessToken, int maxAge);
    Cookie createCookieWithRefreshToken(String refreshToken, int maxAge);
    String extractTokenFromCookie(String name, HttpServletRequest httpServletRequest);
    void deleteCookie(String name, HttpServletResponse httpServletResponse);
}
