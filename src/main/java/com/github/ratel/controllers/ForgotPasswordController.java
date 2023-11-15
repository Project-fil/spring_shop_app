package com.github.ratel.controllers;

import com.github.ratel.payload.request.ForgotPassRequest;
import com.github.ratel.entity.ConfirmToken;
import com.github.ratel.entity.User;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.InvalidTokenException;
import com.github.ratel.exceptions.WrongUserEmail;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.ConfirmTokenService;
import com.github.ratel.services.SendGridMailService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.CheckUtil;
import com.github.ratel.utils.EmailText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("app/shop/")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final EmailText emailText;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SendGridMailService sendGridMailService;
    private final ConfirmTokenService confirmTokenService;

    @PostMapping("free/forgot")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> submitForgotPassword(@Valid @RequestBody ForgotPassRequest payload) {
        User user = this.userService.findUserByEmail(payload.getEmail());
        CheckUtil.checkPassAndConfirmPass(payload.getNewPassword(), payload.getConfirmPassword());
        if (StringUtils.hasText(user.getEmail())) {
            String token = UUID.randomUUID().toString();
            this.confirmTokenService.create(
                    new ConfirmToken(
                            user,
                            token,
                            this.passwordEncoder.encode(payload.getConfirmPassword())
                    )
            );
            this.sendGridMailService.sendMessage(
                    user.getEmail(),
                    "Change Password App_Shop",
                    this.emailText.confirmPass(user.getFirstname(), user.getLastname(), token)
            );
        } else {
            throw new WrongUserEmail(StatusCode.WRONG_USER_EMAIL);
        }
        return ResponseEntity.ok(new MessageResponse("Use your email to confirm your password"));
    }

    @GetMapping("free/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> changePassword(@RequestParam("token") String token) {
        ConfirmToken ct = this.confirmTokenService.findByToken(token);
        if (!ct.isRemoved()) {
            User user = ct.getUser();
            if (user.isRemoved()) {
                throw new EntityNotFoundException("No such user");
            }
            this.userService.updateUser(user.newPass(ct.getNewPass()));
        } else {
            throw new InvalidTokenException("Invalid token");
        }
        return ResponseEntity.status(200).body(new MessageResponse("Password change was successful"));
    }
}
