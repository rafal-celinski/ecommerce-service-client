package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pis24l.projekt.api_client.models.Category;
import pis24l.projekt.api_client.services.CategorySearchService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategorySearchControllerTest {
    @Mock
    private CategorySearchService categorySearchService;

    @InjectMocks
    private CategorySearchController categorySearchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("XD","Category 1"));
        categories.add(new Category("XD", "Category 2"));

        when(categorySearchService.findAll()).thenReturn(categories);

        // When
        List<Category> result = categorySearchController.getAllCategories();

        // Then
        assertEquals(categories.size(), result.size());
        // Add more assertions as needed
    }
}
