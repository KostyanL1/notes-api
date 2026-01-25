package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.model.RefreshToken;
import com.legenkiy.note_api.model.User;

import java.util.List;

public interface RefreshTokenService {

    List<RefreshToken> findAll();
    RefreshToken findByToken(String token);
    void save(String token, User user, String userAgent, String ip);
    RefreshToken findById(Long id);
    void revoke(String token);
    void delete(Long id);



}
