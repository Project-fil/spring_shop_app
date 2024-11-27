package com.github.ratel.services.impl;

import com.github.ratel.entity.User;
import com.github.ratel.entity.VerificationToken;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import com.github.ratel.exceptions.EntityAlreadyExistException;
import com.github.ratel.payload.request.CreateUserRequest;
import com.github.ratel.services.AuthService;
import com.github.ratel.services.SendGridMailService;
import com.github.ratel.services.UserService;
import com.github.ratel.services.VerificationTokenService;
import com.github.ratel.utils.EmailText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailText emailText;
    private final UserService userService;
    private final SendGridMailService sendGridMailService;
    private final VerificationTokenService verificationTokenService;

    @Override
    @Transactional
    public User createUser(Roles role, CreateUserRequest payload) {
        User user = this.userService.createUser(role, payload);
        String token = (UUID.randomUUID().toString());
        this.verificationTokenService.create(new VerificationToken(user, token));
        this.sendGridMailService.sendMessage(
                user.getEmail(),
                "App_Shop user verification",
                this.emailText.regLetter(user.getFirstname(), user.getLastname(), token)
        );
        return user;
    }

    @Override
    @Transactional
    public User verificationUser(String token) {
        VerificationToken vt = verificationTokenService.findByToken(token);
        User user = vt.getUser();
        this.userService.updateUser(user.setVerificationUser(UserVerificationStatus.VERIFIED));
        return user;
    }

    @Override
    public User userAuth(String email, String password) {
        return this.userService.findByEmailAndPassword(email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkAdminIsExist() {
        if (this.userService.findUserByRole(Roles.ROLE_ADMIN)) {
            throw new EntityAlreadyExistException("Administrator already exists");
        }
    }
}
