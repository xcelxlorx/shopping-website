package com.gihae.shop.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "option_tb", indexes = {
        @Index(name = "option_product_id_idx", columnList = "product_id")
})
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100, nullable = false)
    private String name;

    private int price;

    @Builder
    public Option(Long id, Product product, String name, int price) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
    }
}
