package com.rearis.catalog;

import com.rearis.catalog.dto.CategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getAll();
    }

    // GET /api/categories/fustane
    @GetMapping("/{slug}")
    public CategoryResponse getCategory(@PathVariable String slug) {
        return categoryService.getBySlug(slug);
    }
}
