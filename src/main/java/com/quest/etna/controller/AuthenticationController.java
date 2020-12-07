package com.quest.etna.controller;

import com.quest.etna.config.JwtCheckingService;
import com.quest.etna.model.User;
import com.quest.etna.model.Role;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;

@RestController
public class AuthenticationController {

    UserRepository userRepository;

    @Autowired
    private JwtCheckingService jwtCheckingService;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        }
        catch (Exception e){
//            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error");
        }
    }

    @PostMapping("/authenticate")
    ResponseEntity<String> authenticate(@RequestBody User mUser) throws Exception{
        String token = "{ 'token' : }";
        jwtCheckingService.setupUser(mUser.getUsername(), mUser.getPassword());
        String mToken = jwtCheckingService.getValidToken();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(token);
    }

}
