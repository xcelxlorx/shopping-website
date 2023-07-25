package com.gihae.shop.order.controller.dto;

import com.gihae.shop.order.repository.Order;
import com.gihae.shop.orderItem.repository.OrderItem;
import com.gihae.shop.product.repository.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    public static class FindByIdDTO{
        private final int id;
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
            private final int id;
            private final String productName;
            private final List<ItemDTO> items;

            public ProductDTO(Product product, List<OrderItem> orderItems) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = orderItems.stream()
                        .filter(orderItem -> orderItem.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new).collect(Collectors.toList());
            }

            @Getter
            public class ItemDTO{
                private final int id;
                private final String optionName;
                private final int quantity;
                private final int price;

                public ItemDTO(OrderItem orderItem) {
                    this.id = orderItem.getId();
                    this.optionName = orderItem.getOption().getOptionName();
                    this.quantity = orderItem.getQuantity();
                    this.price = orderItem.getOption().getPrice() * orderItem.getQuantity();
                }
            }
        }
    }
}
