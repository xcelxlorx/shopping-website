CREATE SCHEMA IF NOT EXISTS `shop` DEFAULT CHARACTER SET utf8mb4;

use `shop`;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `user_tb`;
DROP TABLE IF EXISTS `product_tb`;
DROP TABLE IF EXISTS `cart_tb`;
DROP TABLE IF EXISTS `option_tb`;
DROP TABLE IF EXISTS `order_tb`;
DROP TABLE IF EXISTS `order_item_tb`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `user_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `email` varchar(100) NOT NULL,
    `password` varchar(256) NOT NULL,
    `username` varchar(45) NOT NULL,
    `role` varchar(30) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
);

CREATE TABLE `product_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` VARCHAR(1000) DEFAULT NULL,
    `image` varchar(500) DEFAULT NULL,
    `price` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `cart_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` bigint(11) UNSIGNED NOT NULL,
    `option_id` bigint(11) UNSIGNED NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_cart_option_user` (`option_id`, `user_id`),
    KEY `cart_user_id_idx` (`user_id`),
    KEY `cart_option_id_idx` (`option_id`)
);

CREATE TABLE `option_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id` bigint(11) UNSIGNED DEFAULT NULL,
    `name` varchar(100) NOT NULL,
    `price` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `option_product_id_idx` (`product_id`),
    CONSTRAINT `option_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`id`)
);

CREATE TABLE `order_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` bigint(11) UNSIGNED NOT NULL,
     PRIMARY KEY (`id`),
     KEY `order_user_id_idx` (`user_id`)
);

CREATE TABLE `order_item_tb` (
    `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `option_id` bigint(11) UNSIGNED NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    `order_id` bigint(11) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY `item_option_id_idx` (`option_id`),
    CONSTRAINT `item_option_id` FOREIGN KEY (`option_id`) REFERENCES `option_tb` (`id`),
    KEY `item_order_id_idx` (`order_id`),
    CONSTRAINT `item_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_tb` (`id`)
);