package com.gihae.shop.cart.repository;

import com.gihae.shop.option.repository.Option;
import com.gihae.shop.user.repository.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "cart_user_id_idx", columnList = "user_id"),
        @Index(name = "cart_option_id_idx", columnList = "option_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "option_id"})
})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Builder
    public Cart(int id, User user, Option option, int quantity, int price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
