package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pis24l.projekt.api_client.model.Category;
import pis24l.projekt.api_client.service.CategorySearchService;

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
        categories.add(new Category("baba","Category 1"));
        categories.add(new Category("kaba", "Category 2"));

        when(categorySearchService.getAllCategories()).thenReturn(categories);

        // When
        List<Category> result = categorySearchController.getAllCategories();

        // Then
        assertEquals(categories.size(), result.size());
        // Add more assertions as needed
    }
}
