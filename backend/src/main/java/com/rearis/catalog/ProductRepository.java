package com.rearis.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    List<Product> findByCategorySlugAndActiveTrue(String slug);

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String text);

    Optional<Product> findByIdAndActiveTrue(Long id);
}