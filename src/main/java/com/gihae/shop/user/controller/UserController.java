package com.gihae.shop.user.controller;

import com.gihae.shop._core.security.JWTProvider;
import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.user.controller.dto.UserRequest;
import com.gihae.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors){
        userService.join(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors){
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.CheckEmailETO requestDTO, Errors errors){
        userService.checkEmail(requestDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
