package com.rearis.catalog.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 200) String name,
        @NotBlank @Size(max = 200) String nameEn,
        String description,
        String descriptionEn,
        @NotNull @Positive BigDecimal price,
        @PositiveOrZero int stock,
        String imageUrl,
        @NotNull Long categoryId
) {
}