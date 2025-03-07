package com.github.ratel.security;

import com.github.ratel.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static UserDetailsImpl fromUserToCustomUserDetails(User user) {
        UserDetailsImpl cud = new UserDetailsImpl();
        cud.id = user.getId();
        cud.email = user.getEmail();
        cud.password = user.getPassword();
        cud.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().toString()));
//        cud.grantedAuthorities = user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getRole()))
//                        .collect(Collectors.toList());
        return cud;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() { return id; }
}
