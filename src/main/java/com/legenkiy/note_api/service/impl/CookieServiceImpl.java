package com.legenkiy.note_api.service.impl;

import com.legenkiy.note_api.service.api.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CookieServiceImpl implements CookieService {

    @Value("${app.cookie.sameSite}")
    private String sameSite;
    @Value("${app.cookie.secure}")
    private boolean secure;


    @Override
    public Cookie createCookieWithToken(String name, String token, int maxAge) {
        Cookie cookieToken = new Cookie(name, token);
        cookieToken.setHttpOnly(true);
        cookieToken.setSecure(secure);
        cookieToken.setPath("/");
        cookieToken.setAttribute("sameSite", sameSite);
        cookieToken.setMaxAge(maxAge);
        return cookieToken;
    }

    @Override
    public String extractTokenFromCookie(String name, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) return null;
        return Arrays.stream(cookies)
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteCookie(String name, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setAttribute("sameSite", sameSite);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }
}

