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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testSearchCategories() {
        // Given
        String search = "searchTerm";

        List<Category> categoryList = new ArrayList<>();
        Category category = new Category("XD", "test");
        categoryList.add(category);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        // When
        List<Category> result = categorySearchService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("test",result.get(0).getName());

    }
}
