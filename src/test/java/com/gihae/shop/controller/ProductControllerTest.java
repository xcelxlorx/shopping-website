package com.gihae.shop.controller;

import com.gihae.shop.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ProductControllerTest extends IntegrationTest {

    @DisplayName("전체 상품 조회")
    @Test
    void findAll_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response[0].id").value(1));
        result.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response[0].description").value(""));
        result.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response[0].price").value(1000));

    }

    @DisplayName("상품 상세 조회")
    @Test
    void findById_test() throws Exception {
        //given
        int productId = 1;

        //when
        ResultActions result = mvc.perform(
                get("/products/{id}", productId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.description").value(""));
        result.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response.price").value(1000));
        result.andExpect(jsonPath("$.response.starCount").value(5));
        result.andExpect(jsonPath("$.response.options[0].id").value(1));
        result.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.options[0].price").value(10000));

    }
}