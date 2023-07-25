package com.gihae.shop.product.controller.dto;

import com.gihae.shop.option.repository.Option;
import com.gihae.shop.product.repository.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    @Getter
    public static class FindAllDTO{
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Getter
    public static class FindByIdDTO{
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount;
        private List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> options) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5; //임시 추가
            this.options = options.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class OptionDTO {
            private int id;
            private String optionName;
            private int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }
}
