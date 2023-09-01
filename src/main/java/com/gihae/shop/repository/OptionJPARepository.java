package com.gihae.shop.repository;

import com.gihae.shop.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") Long productId);
}
