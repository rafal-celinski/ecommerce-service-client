package pis24l.projekt.api_client.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pis24l.projekt.api_client.models.Category;
import pis24l.projekt.api_client.repositories.mongo.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategorySearchServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategorySearchService categorySearchService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // Given
        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category("1", "Electronics");
        Category category2 = new Category("2", "Fashion");
        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        // When
        List<Category> result = categorySearchService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Fashion", result.get(1).getName());
    }

    @Test
    public void testFindById() {
        // Given
        Category category = new Category("1", "Electronics");
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categorySearchService.findById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Electronics", result.get().getName());
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        when(categoryRepository.findById(anyString())).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categorySearchService.findById("1");

        // Then
        assertTrue(result.isEmpty());
    }
}
