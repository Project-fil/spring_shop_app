package com.github.ratel.services.impl;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.Cart;
import com.github.ratel.entity.User;
import com.github.ratel.entity.VerificationToken;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import com.github.ratel.exceptions.EntityAlreadyExistException;
import com.github.ratel.payload.request.CreateUserRequest;
import com.github.ratel.services.*;
import com.github.ratel.utils.CheckUtil;
import com.github.ratel.utils.EmailText;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailText emailText;

    private final CartService cartService;

    private final UserService userService;

    private final AddressService addressService;

    private final PasswordEncoder passwordEncoder;

    private final SendGridMailService sendGridMailService;

    private final VerificationTokenService verificationTokenService;

    @Override
    @Transactional
    public User createUser(Roles roles, CreateUserRequest payload) {
        CheckUtil.checkUserByEmail(this.userService.checkUserByEmail(payload.getEmail()));
        CheckUtil.checkPassAndConfirmPass(payload.getPassword(), payload.getConfirmPassword());
        Address address = new Address();
        address.setPhone(payload.getPhone());
        address = this.addressService.save(address);
        User user = new User();
        user.setFirstname(payload.getFirstname());
        user.setLastname(payload.getLastname());
        user.setEmail(payload.getEmail());
        user.setPassword(this.passwordEncoder.encode(payload.getPassword()));
        user.setVerification(UserVerificationStatus.UNVERIFIED);
        user.setRoles(roles);
        var token = (UUID.randomUUID().toString());
        user.setAddress(address);
        this.userService.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(this.cartService.create(cart));
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
    public void checkAdminIsExist() {
        if (this.userService.findUserByRole(Roles.ROLE_ADMIN)) {
            throw new EntityAlreadyExistException("Administrator already exists");
        }
    }
}
