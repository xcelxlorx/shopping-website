package com.gihae.shop.controller;

import com.gihae.shop.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends IntegrationTest {

    @DisplayName("주문 생성 성공")
    @WithUserDetails(value = "gihae0805@nate.com")
    @Test
    void save_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(209000));
        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));
    }

    @DisplayName("주문 생성 실패 - 인증 실패")
    //@WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    void save_fail_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isUnauthorized());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(jsonPath("$.error.status").value(401));
    }

    @DisplayName("주문 결과 조회")
    @WithMockUser(username = "gihae0805@nate.com", roles = "USER")
    @Test
    void findById_test() throws Exception {
        //given
        int orderId = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(209000));
        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));
    }
}