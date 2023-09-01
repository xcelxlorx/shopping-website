package com.gihae.shop.controller.dto.response;

import com.gihae.shop.domain.Order;
import com.gihae.shop.domain.OrderItem;
import com.gihae.shop.domain.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    public static class FindByIdDTO{
        private final Long id;
        private final List<ProductDTO> products;
        private final int totalPrice;

        public FindByIdDTO(Order order, List<OrderItem> orderItems) {
            this.id = order.getId();
            this.products = orderItems.stream()
                    .map(orderItem -> orderItem.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, orderItems)).collect(Collectors.toList());
            this.totalPrice = orderItems.stream().mapToInt(orderItem -> orderItem.getOption().getPrice() * orderItem.getQuantity()).sum();
        }

        @Getter
        public class ProductDTO{
            private final Long id;
            private final String name;
            private final List<ItemDTO> items;

            public ProductDTO(Product product, List<OrderItem> orderItems) {
                this.id = product.getId();
                this.name = product.getName();
                this.items = orderItems.stream()
                        .filter(orderItem -> orderItem.getOption().getProduct().getId().equals(product.getId()))
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public class ItemDTO{
                private final Long id;
                private final String optionName;
                private final int quantity;
                private final int price;

                public ItemDTO(OrderItem orderItem) {
                    this.id = orderItem.getId();
                    this.optionName = orderItem.getOption().getName();
                    this.quantity = orderItem.getQuantity();
                    this.price = orderItem.getOption().getPrice() * orderItem.getQuantity();
                }
            }
        }
    }
}
