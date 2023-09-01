package com.gihae.shop.controller;

import com.gihae.shop._core.utils.ApiUtils;
import com.gihae.shop.controller.dto.response.ProductResponse;
import com.gihae.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page){
        log.info("product-findAll started");
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        log.info("response=" + responseDTOs);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        log.info("product-findById started");
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        log.info("response=" + responseDTO);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
