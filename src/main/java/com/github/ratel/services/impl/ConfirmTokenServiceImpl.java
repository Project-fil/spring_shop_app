package com.github.ratel.services.impl;

import com.github.ratel.entity.ConfirmToken;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.repositories.ConfirmTokenRepository;
import com.github.ratel.services.ConfirmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmTokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true)
    public ConfirmToken findByToken(String token) {
        if (Objects.isNull(token)) {
            throw new AppException("Invalid parameters value: token(%s)", token);
        }
        return this.tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Нет такого пользователя"));
    }

    @Override
    @Transactional
    public ConfirmToken create(ConfirmToken crt) {
        if (Objects.isNull(crt)) {
            throw new AppException("Invalid parameters value: confirmToken(%s)", crt);
        }
        return this.tokenRepository.save(crt);
    }

}
