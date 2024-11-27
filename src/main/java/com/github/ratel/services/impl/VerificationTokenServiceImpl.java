package com.github.ratel.services.impl;

import com.github.ratel.entity.VerificationToken;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.repositories.VerificationTokenRepository;
import com.github.ratel.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public VerificationToken create(VerificationToken token) {
        return this.tokenRepository.save(token);
    }

    @Override
    @Transactional(readOnly = true)
    public VerificationToken findByToken(String token) {
        return this.tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

}
