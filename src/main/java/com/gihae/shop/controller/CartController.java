package com.gihae.shop.controller;

import com.gihae.shop._core.security.CustomUserDetails;
import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.controller.dto.request.CartRequest;
import com.gihae.shop.controller.dto.response.CartResponse;
import com.gihae.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/carts")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails){
        cartService.addCartList(requestDTOs, userDetails.user());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails){
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.user());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails){
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs, userDetails.user());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
