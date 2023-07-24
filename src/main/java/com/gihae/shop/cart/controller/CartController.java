package com.gihae.shop.cart.controller;

import com.gihae.shop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/carts")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/{id}")
    public ResponseEntity<?> addCartList(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(){
        return ResponseEntity.ok("ok");
    }
}
