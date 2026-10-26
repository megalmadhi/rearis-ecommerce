package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import com.rearis.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllActive() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
        return ProductResponse.from(product);
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<ProductResponse> search(String text) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(text)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}