package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import com.rearis.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rearis.catalog.dto.ProductRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional   // this one WRITES, so no readOnly
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category " + request.categoryId() + " not found"));

        Product product = new Product(
                request.name(), request.nameEn(),
                request.description(), request.descriptionEn(),
                request.price(), request.stock(),
                request.imageUrl(), category
        );

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
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

    public List<ProductResponse> getByCategorySlug(String slug) {
        return productRepository.findByCategorySlugAndActiveTrue(slug)
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