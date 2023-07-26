package com.gihae.shop.orderitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemJPARepository extends JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi join fetch oi.order where oi.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);
}
