package com.github.ratel.services;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;

import java.security.Principal;
import java.util.List;

public interface UserService {

    User getCurrentUser(Principal principal);

    List<User> findAllUsers();

    List<User> findAllUsersForAdmin();

    User findById(Long id);

    User findUserForAdmin(Long userId);

    User findUserByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User save(User user);

    User updateUser(User user);

    String deleteUserById(Long userId);

    User checkUserByEmail(String email);

    boolean findUserByRole(Roles role);

}
