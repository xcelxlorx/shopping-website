package com.gihae.shop.orderItem.repository;

import com.gihae.shop.option.repository.Option;
import com.gihae.shop.order.repository.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "item_option_id_idx", columnList = "option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Builder
    public OrderItem(int id, Option option, Order order, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
