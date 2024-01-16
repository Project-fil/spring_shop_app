package com.github.ratel.services.impl;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.exceptions.ConfirmPasswordException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.repositories.UserRepository;
import com.github.ratel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public User getCurrentUser(Principal principal) {
        return this.findUserByEmail(principal.getName());
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return this.userRepository.findAllByRemovedFalse(pageable);
    }

    @Override
    public Page<User> findAllUsersForAdmin(Pageable pageable) {
        return this.userRepository.findAllByRemovedTrue(pageable);
    }

    @Override
    public User findById(Long id) {
//        return this.userRepository.findByIdAndRemovedFalse(id)
        return this.userRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

    @Override
    public User findUserForAdmin(Long userId) {
        return this.userRepository.findUserByIdForAdmin(userId).orElseThrow(
                () -> new EntityNotFoundException(StatusCode.NOT_FOUND)
        );
    }

    @Override
    public User checkUserByEmail(String email) {
        return this.userRepository.findByEmailAndRemovedFalse(email).orElse(null);
    }

    @Override
    public boolean findUserByRole(Roles role) {
        User user = this.userRepository.findUserByRolesAndRemovedFalse(role).orElse(null);
        return user != null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndRemovedFalse(email)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = this.findUserByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new ConfirmPasswordException(("Wrong password"));
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        this.userRepository.deleteById(userId);
    }

}
