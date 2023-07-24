package com.gihae.shop.user.controller;

import com.gihae.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/check")
    public ResponseEntity<?> check(){
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(){
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("ok");
    }
}
