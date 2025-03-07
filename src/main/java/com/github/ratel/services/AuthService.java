package com.github.ratel.services;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.payload.request.CreateUserRequest;

public interface AuthService {

    User createUser(Roles role, CreateUserRequest payload);

    User verificationUser(String token);

    User userAuth(String email, String password);

    void checkAdminIsExist();

}
