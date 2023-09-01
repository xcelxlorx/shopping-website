package com.gihae.shop.controller.dto.response;

import com.gihae.shop.domain.Option;
import com.gihae.shop.domain.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    @Getter
    public static class FindAllDTO{
        private final Long id;
        private final String productName;
        private final String description;
        private final String image;
        private final int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Getter
    public static class FindByIdDTO{
        private final Long id;
        private final String productName;
        private final String description;
        private final String image;
        private final int price;
        private final int starCount;
        private final List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> options) {
            this.id = product.getId();
            this.productName = product.getName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5; //임시 추가
            this.options = options.stream().map(OptionDTO::new).collect(Collectors.toList());
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
