package com.gihae.shop.order.controller;

import com.gihae.shop._core.security.CustomUserDetails;
import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.order.controller.dto.OrderResponse;
import com.gihae.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails){
        OrderResponse.FindByIdDTO responseDTO = orderService.save(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        OrderResponse.FindByIdDTO responseDTO = orderService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
