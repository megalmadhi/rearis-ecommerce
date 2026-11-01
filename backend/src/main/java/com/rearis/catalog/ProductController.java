package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rearis.catalog.dto.ProductRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.rearis.common.PageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PageResponse<ProductResponse> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 12, sort = "id") Pageable pageable) {

        if (search != null && !search.isBlank()) {
            return productService.search(search, pageable);
        }
        if (categoryId != null) {
            return productService.getByCategory(categoryId, pageable);
        }
        return productService.getAllActive(pageable);
    }

    @GetMapping("/category/{slug}")
    public List<ProductResponse> getProductsByCategory(@PathVariable String slug) {
        return productService.getByCategorySlug(slug);
    }

    // GET /api/products/5
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    // PUT /api/products/5
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    // DELETE /api/products/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();   // 204 No Content
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}