package com.gihae.shop._core.security;

import com.gihae.shop.domain.User;
import com.gihae.shop.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findUser = userJPARepository.findByEmail(email);

        if (findUser.isEmpty()) {
            log.warn("login failed");
            return null;
        }else{
            User user = findUser.get();
            return new CustomUserDetails(user);
        }
    }
}
