package com.legenkiy.note_api.service.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    Cookie createCookieWithToken(String name, String token, int maxAge);

    String extractTokenFromCookie(String name, HttpServletRequest httpServletRequest);

    void deleteCookie(String name, HttpServletResponse httpServletResponse);
}
