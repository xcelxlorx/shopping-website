package com.gihae.shop.service;

import com.gihae.shop._core.errors.exception.Exception400;
import com.gihae.shop._core.errors.exception.Exception500;
import com.gihae.shop._core.security.JWTProvider;
import com.gihae.shop.controller.dto.request.UserRequest;
import com.gihae.shop.domain.User;
import com.gihae.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO){
        checkEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        try {
            userRepository.save(requestDTO.toEntity());
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    public String login(UserRequest.LoginDTO requestDTO){
        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception400("이메일을 찾을 수 없습니다 : " + requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){
            throw new Exception400("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(user);
    }

    public void checkEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new Exception400("동일한 이메일이 존재합니다 : " + email);
        }
    }
}
