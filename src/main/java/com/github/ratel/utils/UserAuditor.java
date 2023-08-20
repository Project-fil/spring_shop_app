package com.github.ratel.utils;

import com.github.ratel.entity.User;
import com.github.ratel.security.UserDetailsImpl;
import com.github.ratel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuditor  implements AuditorAware<User> {

    private final UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        try {
            return Optional.of(this.userService.findUserByEmail(userDetails.getUsername()));
        } catch (Throwable ignored) {
        }
        return Optional.empty();
    }
}
