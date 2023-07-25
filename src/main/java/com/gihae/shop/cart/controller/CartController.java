package com.gihae.shop.cart.controller;

import com.gihae.shop._core.exception.Exception400;
import com.gihae.shop._core.security.CustomUserDetails;
import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.cart.controller.dto.CartRequest;
import com.gihae.shop.cart.controller.dto.CartResponse;
import com.gihae.shop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/carts")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails){
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        cartService.addCartList(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails){
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails){
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
