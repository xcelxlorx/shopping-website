package com.gihae.shop.product.controller;

import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.product.controller.dto.ProductResponse;
import com.gihae.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page){
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
