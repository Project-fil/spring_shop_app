package com.github.ratel.services.impl;

import com.github.ratel.entity.ConfirmToken;
import com.github.ratel.repositories.ConfirmTokenRepository;
import com.github.ratel.services.ConfirmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmTokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true)
    public ConfirmToken findByToken(String token) {
        return this.tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Нет такого пользователя"));
    }

    @Override
    @Transactional
    public ConfirmToken create(ConfirmToken crt) {
        return this.tokenRepository.save(crt);
    }

}
