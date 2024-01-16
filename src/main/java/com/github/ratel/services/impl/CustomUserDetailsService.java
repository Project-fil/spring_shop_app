package com.github.ratel.services.impl;

import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.repositories.UserRepository;
import com.github.ratel.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.fromUserToCustomUserDetails(userRepository.findByEmailAndRemovedFalse(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден")));
    }

    public UserDetailsImpl loadUserByLongId(Long userId) throws UsernameNotFoundException {
        return UserDetailsImpl.fromUserToCustomUserDetails(userRepository.findByIdAndRemovedFalse(userId)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND)));
    }

}
