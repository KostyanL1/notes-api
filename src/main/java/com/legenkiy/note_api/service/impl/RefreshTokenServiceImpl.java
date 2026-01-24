package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.model.RefreshToken;
import com.legenkiy.note_api.repository.RefreshTokenRepository;
import com.legenkiy.note_api.service.api.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public List<RefreshToken> findAll() {
        return refreshTokenRepository.findAll();
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found!"));
    }
    @Transactional
    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findById(Long id) {
        return refreshTokenRepository.findById(id).orElseThrow(() -> new RuntimeException("Token not found!"));
    }


    @Override
    public void revoke(String token) {
        RefreshToken refreshToken = findByToken(token);
        refreshToken.setRevoked(true);
    }

    @Override
    public void delete(Long id) {
        if (refreshTokenRepository.existsById(id)){
            refreshTokenRepository.deleteById(id);
        }else {
            throw new RuntimeException("Token wasn`t deleted. Token doesn`t exist");
        }

    }
}
