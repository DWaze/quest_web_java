package com.quest.etna.controller;

import com.quest.etna.config.JwtCheckingService;
import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.Role;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtCheckingService jwtCheckingService;

    @Autowired
    public AuthenticationController(UserRepository userRepository, JwtTokenUtil jwtTokenUtil,
                                    JwtCheckingService jwtCheckingService) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtCheckingService = jwtCheckingService;
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody User mUser){
        try {
            boolean exists = userRepository.existsUserByUsername(mUser.getUsername());
            if (exists) {
                throw new DuplicateKeyException("duplicata");
            }
            Date sqlDate = new Date(new java.util.Date().getTime());
            mUser.setRole(Role.ROLE_USER);
            mUser.setCreationDate(sqlDate);
            mUser.setUpdatedDate(sqlDate);
            userRepository.save(mUser);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mUser.toString());
        }catch (DuplicateKeyException e){
//            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("duplicata");
        }catch (Exception e){
//            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error");
        }
    }

    @PostMapping("/authenticate")
    ResponseEntity<String> authenticate(@RequestBody User mUser){
        try {
            jwtCheckingService.setupUser(mUser.getUsername(), mUser.getPassword());
            String mToken = jwtCheckingService.getValidToken();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mToken);
        }catch (Exception ex){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Mauvais utilisateur / mot de passe");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(@RequestHeader(name = "Authorization")String bearerToken){
        try {
            String token = bearerToken.substring(7);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            User user = userRepository.findByUsername(userName);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user.toString());
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error");
        }
    }

}
