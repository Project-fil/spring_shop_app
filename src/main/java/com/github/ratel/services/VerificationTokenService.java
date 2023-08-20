package com.github.ratel.services;

import com.github.ratel.entity.VerificationToken;

public interface VerificationTokenService {

    VerificationToken create(VerificationToken crt);

    VerificationToken findByToken(String token);

}
