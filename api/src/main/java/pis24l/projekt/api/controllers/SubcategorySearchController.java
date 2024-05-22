package pis24l.projekt.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api.model.Subcategory;
import pis24l.projekt.api.service.SubcategorySearchService;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubcategorySearchController {

    private final SubcategorySearchService subcategoryService;

    @Autowired
    public SubcategorySearchController(SubcategorySearchService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public List<Subcategory> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @GetMapping("/category")
    public List<Subcategory> getSubcategoriesByCategoryId(@RequestParam Long categoryId) {
        return subcategoryService.getSubcategoriesByCategoryId(categoryId);
    }

}