package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pis24l.projekt.api_client.models.Category;
import pis24l.projekt.api_client.models.Subcategory;
import pis24l.projekt.api_client.services.CategorySearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategorySearchControllerTest {
    @Mock
    private CategorySearchService categorySearchService;

    @InjectMocks
    private CategorySearchController categorySearchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("1", "Category 1"));
        categories.add(new Category("2", "Category 2"));

        when(categorySearchService.findAll()).thenReturn(categories);

        // When
        List<Category> result = categorySearchController.getAllCategories();

        // Then
        assertEquals(categories.size(), result.size());
        // Add more assertions as needed
    }

    @Test
    public void testGetSubcategoriesByCategoryId_CategoryFound() {
        // Given
        String categoryId = "1";
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(new Subcategory("1"));
        subcategories.add(new Subcategory("2"));
        Category category = new Category(categoryId, "Category 1");
        category.setSubcategories(subcategories);

        when(categorySearchService.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        ResponseEntity<List<Subcategory>> response = categorySearchController.getSubcategoriesByCategoryId(categoryId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subcategories.size(), response.getBody().size());
    }

    @Test
    public void testGetSubcategoriesByCategoryId_CategoryNotFound() {
        // Given
        String categoryId = "2";

        when(categorySearchService.findById(categoryId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<List<Subcategory>> response = categorySearchController.getSubcategoriesByCategoryId(categoryId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
