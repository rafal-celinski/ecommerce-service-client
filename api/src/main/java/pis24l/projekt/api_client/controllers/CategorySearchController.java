package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.models.Category;
import pis24l.projekt.api_client.models.Subcategory;
import pis24l.projekt.api_client.services.CategorySearchService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/categories")
public class CategorySearchController {

    private final CategorySearchService categorySearchService;
    @Autowired
    public CategorySearchController(CategorySearchService categorySearchService) {
        this.categorySearchService = categorySearchService;
    }
    @GetMapping
    public List<Category> getAllCategories() {
        return categorySearchService.findAll();
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategoryId(@PathVariable String id) {
        Optional<Category> category = categorySearchService.findById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get().getSubcategories());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
