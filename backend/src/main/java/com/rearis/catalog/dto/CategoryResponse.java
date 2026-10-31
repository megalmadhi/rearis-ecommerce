package com.rearis.catalog.dto;

import com.rearis.catalog.Category;

public record CategoryResponse(Long id, String name, String nameEn, String slug) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getNameEn(),
                category.getSlug()
        );
    }
}