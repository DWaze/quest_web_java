package com.quest.etna.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;

public class JwtUserDetails implements UserDetails {

    private User user;
    private org.springframework.security.core.userdetails.User securityUser;
    private PasswordEncoder passwordEncoder;


    public JwtUserDetails(User user, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.user = user;
        this.securityUser = new org.springframework.security.core.userdetails.User(
                getUsername(),
                getPassword(),
                new ArrayList<>()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.securityUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.passwordEncoder.encode(this.user.getPassword());
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
}
