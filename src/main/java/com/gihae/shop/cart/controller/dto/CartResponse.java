package com.gihae.shop.cart.controller.dto;

import com.gihae.shop.cart.repository.Cart;
import com.gihae.shop.option.repository.Option;
import com.gihae.shop.product.repository.Product;
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
            private final int id;
            private final String productName;
            private final List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> carts) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = carts.stream().map(CartDTO::new).collect(Collectors.toList());
            }

            @Getter
            public class CartDTO{
                private final int id;
                private final OptionDTO option;
                private final int quantity;
                private final int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
                }

                @Getter
                public class OptionDTO {
                    private final int id;
                    private final String optionName;
                    private final int price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
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
            private final int cartId;
            private final int optionId;
            private final String optionName;
            private final int quantity;
            private final int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }
}
