package com.quest.etna.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/testSuccess")
    ResponseEntity<String> testSuccess(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("success");
    }

    @GetMapping("/testNotFound")
    ResponseEntity<String> testNotFound(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("not found");
    }

    @GetMapping("/testError")
    ResponseEntity<String> testError(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("error");
    }
}
