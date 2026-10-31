package com.rearis.catalog.dto;

import java.math.BigDecimal;

import com.rearis.catalog.Product;

public record ProductResponse(Long id, String name, String nameEn, String description, String descriptionEn, BigDecimal price, int stock, String imageUrl, Long categoryId, String categoryName, String categoryNameEn) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getNameEn(),
                product.getDescription(),
                product.getDescriptionEn(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getNameEn()
        );
    }
}
