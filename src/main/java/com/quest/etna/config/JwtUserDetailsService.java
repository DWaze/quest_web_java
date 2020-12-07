package com.quest.etna.config;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.quest.etna.model.User user = userRepository.findByUsername(username);
        return new JwtUserDetails(user);
    }
}
