package com.gihae.shop.option.repository;

import com.gihae.shop.product.repository.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "option_product_id_idx", columnList = "product_id")
})
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;

    @Column(length = 100, nullable = false)
    private String optionName;

    private int price;

    @Builder
    public Option(int id, Product product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
