package com.github.ratel.services.impl;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.exceptions.ConfirmPasswordException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.repositories.UserRepository;
import com.github.ratel.services.UserService;
import lombok.RequiredArgsConstructor;
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
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersForAdmin() {
        return this.userRepository.findAllByRemovedTrue();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

    @Override
    public User findUserForAdmin(Long userId) {
        return this.userRepository.findUserByIdAndRemovedTrue(userId).orElseThrow(
                () -> new EntityNotFoundException(StatusCode.NOT_FOUND)
        );
    }

    @Override
    public User checkUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean findUserByRole(Roles role) {
        User user = this.userRepository.findUserByRoles(role).orElse(null);
        return user != null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
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
    public String deleteUserById(Long userId) {
        User user = this.findById(userId);
        this.userRepository.deleteById(userId);
        return user.getFirstname() + " " + user.getLastname();
    }

}
