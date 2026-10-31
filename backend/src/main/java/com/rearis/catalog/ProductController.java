package com.rearis.catalog;

import com.rearis.catalog.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rearis.catalog.dto.ProductRequest;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
    // GET /api/products?categoryId=2
    // GET /api/products?search=kostum
    @GetMapping
    public List<ProductResponse> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search) {

        if (search != null && !search.isBlank()) {
            return productService.search(search);
        }
        if (categoryId != null) {
            return productService.getByCategory(categoryId);
        }
        return productService.getAllActive();
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

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}