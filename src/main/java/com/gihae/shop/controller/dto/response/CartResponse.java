package com.gihae.shop.controller.dto.response;

import com.gihae.shop.domain.Cart;
import com.gihae.shop.domain.Option;
import com.gihae.shop.domain.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    @Getter
    public static class FindAllDTO{
        private final List<ProductDTO> products;
        private final int totalPrice;

        public FindAllDTO(List<Cart> carts) {
            this.products = carts.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, carts)).collect(Collectors.toList());
            this.totalPrice = carts.stream()
                    .mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        public class ProductDTO {
            private final Long id;
            private final String name;
            private final List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> carts) {
                this.id = product.getId();
                this.name = product.getName();
                this.carts = carts.stream()
                        .filter(cart -> cart.getOption().getProduct().getId().equals(product.getId()))
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public class CartDTO{
                private final Long id;
                private final OptionDTO option;
                private final int quantity;
                private final int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                public class OptionDTO {
                    private final Long id;
                    private final String name;
                    private final int price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.name = option.getName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }

    @Getter
    public static class UpdateDTO {
        private final List<CartDTO> carts;
        private final int totalPrice;

        public UpdateDTO(List<Cart> carts) {
            this.carts = carts.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = carts.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter
        public class CartDTO {
            private final Long cartId;
            private final Long optionId;
            private final String optionName;
            private final int quantity;
            private final int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }
}
