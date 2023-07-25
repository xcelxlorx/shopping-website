package com.gihae.shop.product.service;

import com.gihae.shop.option.repository.Option;
import com.gihae.shop.option.repository.OptionJPARepository;
import com.gihae.shop.product.controller.dto.ProductResponse;
import com.gihae.shop.product.repository.Product;
import com.gihae.shop.product.repository.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productRepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page){
        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> pageContent = productRepository.findAll(pageable);
        return pageContent.getContent().stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponse.FindByIdDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow();
        List<Option> options = optionJPARepository.findByProductId(id);
        return new ProductResponse.FindByIdDTO(product, options);
    }
}
