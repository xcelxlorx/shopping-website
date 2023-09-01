package com.gihae.shop.repository;

import com.gihae.shop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPARepository extends JpaRepository<Order, Long> {
}
