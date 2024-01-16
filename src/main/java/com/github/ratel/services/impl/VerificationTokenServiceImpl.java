package com.github.ratel.services.impl;

import com.github.ratel.entity.VerificationToken;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.repositories.VerificationTokenRepository;
import com.github.ratel.services.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ratel.exceptions.EntityNotFoundException;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public VerificationToken create(VerificationToken token) {
        return this.tokenRepository.save(token);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return this.tokenRepository.findByTokenAndRemovedFalse(token)
                .orElseThrow( () -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

}
