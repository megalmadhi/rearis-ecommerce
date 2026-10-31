package com.rearis.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    List<Product> findByCategorySlugAndActiveTrue(String slug);

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String text);
}