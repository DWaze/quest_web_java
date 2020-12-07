package com.quest.etna.config;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtCheckingService {

    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    private String username;
    private String password;
    private JwtUserDetails userDetails;

    @Autowired
    public JwtCheckingService(UserRepository userRepo, JwtTokenUtil jwtTokenUtil,
                              AuthenticationManager authenticationManager) {
        this.userRepository = userRepo;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public void setupUser(String username, String password) {
        this.username = username;
        this.password = password;
        User user = userRepository.findByUsername(username);
        this.userDetails = new JwtUserDetails(user);
        log.info(user.toString());
    }

    public String getValidToken() throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(this.username, this.password)
            );
            return jwtTokenUtil.generateToken(userDetails);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("invalid username/password");
        }
    }
}
