package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pis24l.projekt.api_client.model.Category;
import pis24l.projekt.api_client.service.CategorySearchService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5000")
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
        return categorySearchService.getAllCategories();
    }

}
