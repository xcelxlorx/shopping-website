package com.gihae.shop.cart.controller;

import com.gihae.shop._core.security.CustomUserDetails;
import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.cart.controller.dto.CartRequest;
import com.gihae.shop.cart.controller.dto.CartResponse;
import com.gihae.shop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/carts")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails){
        cartService.addCartList(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails){
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails){
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
