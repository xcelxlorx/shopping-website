package com.gihae.shop.repository;

import com.gihae.shop._core.util.DummyEntity;
import com.gihae.shop.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest extends DummyEntity {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository.save(newUser("gihae"));
    }

    @Test
    @DisplayName("유저 이메일로 찾기")
    void findByEmail() {
        //given
        String email = "gihae@naver.com";

        //when
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일로 유저를 찾을 수 없습니다.")
        );

        //then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("gihae@naver.com");
        assertTrue(BCrypt.checkpw("gihae1234!", user.getPassword()));
        assertThat(user.getUsername()).isEqualTo("gihae");
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("유저 아이디로 찾기")
    void findById(){
        //given
        Long id = 1L;

        //when
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 아이디로 유저를 찾을 수 없습니다.")
        );

        //then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("gihae@naver.com");
        assertTrue(BCrypt.checkpw("gihae1234!", user.getPassword()));
        assertThat(user.getUsername()).isEqualTo("gihae");
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    void save(){}

}
