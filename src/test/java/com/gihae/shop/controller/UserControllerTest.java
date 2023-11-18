package com.gihae.shop.controller;

import com.gihae.shop.IntegrationTest;
import com.gihae.shop._core.security.JWTProvider;
import com.gihae.shop.controller.dto.request.UserRequest;
import com.gihae.shop.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    @DisplayName("회원가입 성공")
    @Test
    void join_success_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("gihae0805@nate.com");
        requestDTO.setPassword("gihae1234!");
        requestDTO.setUsername("gihae0805");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value(true));
    }

    @DisplayName("회원가입 실패 - 이메일 형식 오류")
    @Test
    void join_fail_test1() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("gihae0805nate.com");
        requestDTO.setPassword("gihae1234!");
        requestDTO.setUsername("gihae0805");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }

    @DisplayName("회원가입 실패 - 비밀번호 형식 오류")
    @Test
    void join_fail_test2() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("gihae0805@nate.com");
        requestDTO.setPassword("gihae1234");
        requestDTO.setUsername("gihae0805");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }

    @DisplayName("회원가입 실패 - 이름 길이 오류")
    @Test
    void join_fail_test3() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("gihae0805@nate.com");
        requestDTO.setPassword("gihae1234!");
        requestDTO.setUsername("gihae0805");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success_test() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("gihae0805@nate.com");
        loginDTO.setPassword("gihae1234!");
        User user = User.builder().id(1L).role("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
    }

    @DisplayName("로그인 실패 - 이메일 형식 오류")
    @Test
    void login_fail_test1() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("gihae0805nate.com");
        loginDTO.setPassword("gihae1234!");
        User user = User.builder().id(1L).role("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요:email"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }

    @DisplayName("로그인 실패 - 비밀번호 형식 오류")
    @Test
    void login_fail_test2() throws Exception {
        // given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("gihae0805@nate.com");
        loginDTO.setPassword("gihae1234");
        User user = User.builder().id(1L).role("ROLE_USER").build();
        String requestBody = om.writeValueAsString(loginDTO);

        // when
        ResultActions result = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("responseBody=" + responseBody);
        System.out.println("responseHeader=" + responseHeader);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }

    @DisplayName("이메일 중복 확인 성공")
    @Test
    void checkEmail_success_test() throws Exception {
        //given
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("gihae0805@nate.com");
        String requestBody = om.writeValueAsString(checkEmailDTO);

        //when
        ResultActions result = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("이메일 중복 확인 실패")
    @Test
    void checkEmail_fail_test() throws Exception {
        //given
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("gihae0805@nate.com");
        String requestBody = om.writeValueAsString(checkEmailDTO);

        //when
        ResultActions result = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
        result.andExpect(jsonPath("$.error.status").value(400));
    }
}