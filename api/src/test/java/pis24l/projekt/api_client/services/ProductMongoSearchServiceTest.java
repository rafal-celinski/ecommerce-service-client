package pis24l.projekt.api_client.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductMongoSearchServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ProductMongoSearchService productMongoSearchService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchProducts_withAllParameters() {
        // Given
        String search = "searchTerm";
        String category = "category";
        String subcategory = "subcategory";
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = Collections.singletonList(
                new Product("title", BigDecimal.valueOf(20), "Warsaw", "subcategory", "category", "description", ProductStatus.UP));

        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(productList);
        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);

        // When
        Page<Product> result = productMongoSearchService.listCart(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
    }

    @Test
    public void testSearchProducts_withoutCategory() {
        // Given
        String search = "searchTerm";
        String category = null;
        String subcategory = "subcategory";
        String location = "location";
        ProductStatus status = ProductStatus.UP;
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = Collections.singletonList(
                new Product("title", BigDecimal.valueOf(20), "Warsaw", "subcategory", "category", "description", ProductStatus.UP));

        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(productList);
        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);

        // When
        Page<Product> result = productMongoSearchService.listDone(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
    }

}
