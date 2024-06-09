package pis24l.projekt.api_client.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pis24l.projekt.api_client.model.Image;
import pis24l.projekt.api_client.model.Product;
import pis24l.projekt.api_client.repositories.ImageRepository;
import pis24l.projekt.api_client.repositories.ProductRepository;
import pis24l.projekt.api_client.service.ProductSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductSearchServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ProductSearchService productSearchService;



    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);// Manually inject the mock EntityManager
    }

    private void setupMockQuery(List<Product> productList, long total) {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Product> criteriaQuery = mock(CriteriaQuery.class);
        Root<Product> root = mock(Root.class);
        TypedQuery<Product> typedQuery = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Product.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Product.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productList);

        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);

        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);
        Root<Product> countRoot = mock(Root.class);
        TypedQuery<Long> countTypedQuery = mock(TypedQuery.class);

        when(criteriaBuilder.createQuery(Long.class)).thenReturn(countQuery);
        when(countQuery.from(Product.class)).thenReturn(countRoot);
        when(countQuery.select(any())).thenReturn(countQuery); // Mocking the select method
        when(countQuery.where(any(Predicate[].class))).thenReturn(countQuery); // Mocking the where method

        when(countTypedQuery.getSingleResult()).thenReturn(total);
        when(entityManager.createQuery(countQuery)).thenReturn(countTypedQuery);
    }


    @Test
    public void testSearchProducts_withAllParameters() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String search = "searchTerm";
        Long category = 1L;
        Long subcategory = 2L;
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");
        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_withoutCategory() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String search = "searchTerm";
        Long category = null;
        Long subcategory = 2L;
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_withoutSubcategory() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String search = "searchTerm";
        Long category = 1L;
        Long subcategory = null;
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_withoutPriceRange() {
        // Given
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        String search = "searchTerm";
        Long category = 1L;
        Long subcategory = 2L;
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_withoutLocation() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String search = "searchTerm";
        Long category = 1L;
        Long subcategory = 2L;
        String location = null;
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_noCriteria() {
        // Given
        String search = "";
        Long category = null;
        Long subcategory = null;
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        String location = "";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSearchProducts_withPagination() {
        // Given
        String search = "searchTerm";
        Long category = 1L;
        Long subcategory = 2L;
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", 0L, 0L, "xd");        productList.add(product);
        long total = 1;

        setupMockQuery(productList, total);

        // When
        Page<Product> result = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testGetProductById_withImages() {
        // Mock data
        String productId = "XDXD";
        byte[] imageData = new byte[] {0,2,3,4};
        Product product = new Product(productId,"XD",BigDecimal.valueOf(0));


        // Mock behaviorv
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(imageRepository.findByProductId(productId)).thenReturn(Collections.singletonList(new Image(0L)));

        // Call the method
        Product result = productSearchService.getProductById(productId);

        // Verify
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertFalse(result.getImageUrls().isEmpty()); // Check if the list of URLs is not empty
    }
    @Test
    void testGetProductById_notFound() {
        // Mock data
        when(productRepository.findById("XDXD")).thenReturn(Optional.empty());

        // Test & Verify
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productSearchService.getProductById("XDXD");
        });
        assertEquals("Product not found with id 1", exception.getMessage());

        verify(productRepository, times(1)).findById("XDXD");
    }


}
