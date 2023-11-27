package com.github.ratel.controllers;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import com.github.ratel.exceptions.UnverifiedException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.payload.request.CreateUserRequest;
import com.github.ratel.payload.request.UserAuthRequest;
import com.github.ratel.payload.response.TokenResponse;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.security.JwtTokenProvider;
import com.github.ratel.security.UserDetailsImpl;
import com.github.ratel.services.AuthService;
import com.github.ratel.utils.transfer_object.UserTransferObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/app/shop/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @CrossOrigin("*")
    @PostMapping("free/create/admin")
    public ResponseEntity<Object> registrationAdmin(@RequestBody @Valid CreateUserRequest payload) {
        this.authService.checkAdminIsExist();
        User user = this.authService.createUser(Roles.ROLE_ADMIN, payload);
        return ResponseEntity.ok(UserTransferObj.fromUser(user));
    }

    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/create/manager")
    public ResponseEntity<UserResponse> createManager(CreateUserRequest payload) {
        User user = this.authService.createUser(Roles.ROLE_MANAGER, payload);
        return ResponseEntity.ok(UserTransferObj.fromUser(user));
    }

    @CrossOrigin("*")
    @PostMapping("free/registration")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest payload) {
        User user = this.authService.createUser(Roles.ROLE_USER, payload);
        return ResponseEntity.ok(UserTransferObj.fromUser(user));
    }

    @CrossOrigin("*")
    @GetMapping("free/verification")
    public ResponseEntity<Object> passingVerification(@RequestParam("token") String token) {
        User user = this.authService.verificationUser(token);
        return ResponseEntity.ok(
                new TokenResponse(
                        this.tokenProvider.generateToken(UserDetailsImpl.fromUserToCustomUserDetails(user)),
                        user.getId(),
                        user.getFirstname(),
                        user.getRoles()
                )
        );
    }

    @CrossOrigin("*")
    @PostMapping("free/authorization")
    public ResponseEntity<Object> auth(@RequestBody @Valid UserAuthRequest userAuthRequest) {
        User user = this.authService.userAuth(userAuthRequest.getEmail(), userAuthRequest.getPassword());
        if (user.getVerification().equals(UserVerificationStatus.UNVERIFIED)) {
            throw new UnverifiedException(StatusCode.USER_NOT_VERIFIED);
        } else {
            return ResponseEntity.ok(
                    new TokenResponse(
                            this.tokenProvider.generateToken(UserDetailsImpl.fromUserToCustomUserDetails(user)),
                            user.getId(),
                            user.getFirstname(),
                            user.getRoles()
                    )
            );
        }
    }

}
