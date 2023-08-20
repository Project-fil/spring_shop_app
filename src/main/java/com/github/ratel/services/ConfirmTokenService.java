package com.github.ratel.services;

import com.github.ratel.entity.ConfirmToken;

public interface ConfirmTokenService {

    ConfirmToken findByToken(String token);

    ConfirmToken create(ConfirmToken confirmToken);

    ConfirmToken update(ConfirmToken confirmToken);
}
