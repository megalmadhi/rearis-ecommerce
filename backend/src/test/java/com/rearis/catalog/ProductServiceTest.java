package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import com.rearis.common.NotFoundException;
import com.rearis.catalog.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)   // lets Mockito create the mocks
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;    // fake repository

    @Mock
    CategoryRepository categoryRepository;  // fake repository

    @InjectMocks
    ProductService productService;          // the real service, built with the two fakes

    @Test
    void getById_returnsProduct_whenItExists() {
        // Arrange
        Category category = new Category("Kostume", "Suits", "kostume");
        Product product = new Product("Kostum klasik blu", "Classic Navy Suit",
                "Kostum me dy pjesë", "Two-piece suit",
                new BigDecimal("24500.00"), 8, null, category);
        when(productRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(product));

        // Act
        ProductResponse result = productService.getById(1L);

        // Assert
        assertThat(result.name()).isEqualTo("Kostum klasik blu");
        assertThat(result.price()).isEqualByComparingTo("24500.00");
    }

    @Test
    void getById_throwsNotFound_whenProductIsMissing() {
        // Arrange
        when(productRepository.findByIdAndActiveTrue(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> productService.getById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_throwsNotFound_whenCategoryDoesNotExist() {
        // Arrange: the fake category repository finds nothing for id 5
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());

        ProductRequest request = new ProductRequest(
                "Fustan i kuq", "Red Dress",
                "Fustan veror", "Summer dress",
                new BigDecimal("7500.00"), 10, null,
                5L);   // categoryId 5 doesn't exist

        // Act + Assert: create must throw NotFoundException
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(NotFoundException.class);

        // Assert: save was never called
        verify(productRepository, never()).save(any());
    }
}