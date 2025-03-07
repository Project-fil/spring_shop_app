package com.github.ratel.services.impl;

import com.github.ratel.entity.User;
import com.github.ratel.entity.VerificationToken;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityAlreadyExistException;
import com.github.ratel.exceptions.UnverifiedException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.payload.request.CreateUserRequest;
import com.github.ratel.services.AuthService;
import com.github.ratel.services.SendGridMailService;
import com.github.ratel.services.UserService;
import com.github.ratel.services.VerificationTokenService;
import com.github.ratel.utils.EmailText;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
        if (ObjectUtils.anyNull(role, payload)) {
            throw new AppException("Invalid parameters value: role(%s) or CreateUserRequest", role);
        }
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
        if (Objects.isNull(token)) {
            throw new AppException("Invalid parameter token value");
        }
        VerificationToken vt = verificationTokenService.findByToken(token);
        User user = vt.getUser();
        this.userService.updateUser(user.setVerificationUser(UserVerificationStatus.VERIFIED));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User userAuth(String email, String password) {
        User user = this.userService.findByEmailAndPassword(email, password);
        this.checkVerification(user.getVerification());
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public void checkAdminIsExist() {
        if (this.userService.findUserByRole(Roles.ROLE_ADMIN)) {
            throw new EntityAlreadyExistException("Administrator already exists");
        }
    }

    private void checkVerification(UserVerificationStatus status) throws UnverifiedException {
        if (Objects.nonNull(status) && status.equals(UserVerificationStatus.UNVERIFIED)) {
            throw new UnverifiedException(StatusCode.USER_NOT_VERIFIED);
        }
    }

}
