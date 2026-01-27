package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.dto.AuthTokens;
import com.legenkiy.note_api.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;


public interface AuthService {

    AuthTokens register(UserDto userDto, HttpServletRequest httpServletRequest);

    AuthTokens login(UserDto userDto, HttpServletRequest httpServletRequest);

    AuthTokens refresh(HttpServletRequest httpServletRequest);

    void logout(HttpServletRequest httpServletRequest);

}
