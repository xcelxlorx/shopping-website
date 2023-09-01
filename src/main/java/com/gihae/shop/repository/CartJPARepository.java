package com.gihae.shop.repository;

import com.gihae.shop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartJPARepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);

    @Query("select c from Cart c join fetch c.option where c.user.id = :userId")
    List<Cart> findAllByUserIdJoinFetch(@Param("userId") Long userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") Long optionId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Cart c set c.quantity = :#{#cart.quantity}, c.price = :#{#cart.price} where c.id = :#{#cart.id}")
    void update(@Param("cart") Cart cart);
}
