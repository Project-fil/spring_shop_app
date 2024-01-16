package com.github.ratel.services;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface UserService {

    User getCurrentUser(Principal principal);

    Page<User> findAllUsers(Pageable pageable);

    Page<User> findAllUsersForAdmin(Pageable pageable);

    User findById(Long id);

    User findUserForAdmin(Long userId);

    User findUserByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User save(User user);

    User updateUser(User user);

    void deleteUserById(Long userId);

    User checkUserByEmail(String email);

    boolean findUserByRole(Roles role);

}
