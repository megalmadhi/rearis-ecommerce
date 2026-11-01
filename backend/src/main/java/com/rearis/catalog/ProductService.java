package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import com.rearis.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rearis.catalog.dto.ProductRequest;
import com.rearis.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product " + id + " not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category " + request.categoryId() + " not found"));

        product.update(
                request.name(), request.nameEn(),
                request.description(), request.descriptionEn(),
                request.price(), request.stock(),
                request.imageUrl(), category
        );

        return ProductResponse.from(product);   // no save(): dirty checking stores the changes
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product " + id + " not found"));  // same as the first line of update()

        product.deactivate();          // call the method from step 1
    }                            // no save(): dirty checking writes the UPDATE

    public PageResponse<ProductResponse> getAllActive(Pageable pageable) {
        Page<ProductResponse> page = productRepository.findByActiveTrue(pageable)
                .map(ProductResponse::from);
        return PageResponse.from(page);
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
        return ProductResponse.from(product);
    }

    public PageResponse<ProductResponse> getByCategory(Long categoryId, Pageable pageable) {
        Page<ProductResponse> page = productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable)
                .map(ProductResponse::from);
        return PageResponse.from(page);
    }

    public PageResponse<ProductResponse> search(String text, Pageable pageable) {
        Page<ProductResponse> page = productRepository.findByNameContainingIgnoreCaseAndActiveTrue(text, pageable)
                .map(ProductResponse::from);
        return PageResponse.from(page);
    }

    public List<ProductResponse> getByCategorySlug(String slug) {
        return productRepository.findByCategorySlugAndActiveTrue(slug)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}