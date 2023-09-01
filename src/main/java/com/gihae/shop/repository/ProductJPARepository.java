package com.gihae.shop.repository;

import com.gihae.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
}
