package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pis24l.projekt.api_client.model.Product;
import pis24l.projekt.api_client.service.ProductSearchService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProductSearchControllerTest {

    private MockMvc mockMvc;


    @Mock
    private ProductSearchService productSearchService;

    @InjectMocks
    private ProductSearchController productSearchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productSearchController).build();
    }



    @Test
    public void testSearchProducts_withAllParameters() {
        // Given
        BigDecimal minPrice = BigDecimal.valueOf(0);
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        String search = "searchTerm";
        String category = "dg";
        String subcategory = "asjnf";
        String location = "location";
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> productList = new ArrayList<>();
        Product product = new Product("xd", BigDecimal.valueOf(20), "Warszawa", "asf", "dsg", "xd");
        productList.add(product);
        long total = 1;

        when(productSearchService.searchProducts(anyString(), anyString(), anyString(), any(BigDecimal.class), any(BigDecimal.class), anyString(), eq(pageable)))
                .thenReturn(new PageImpl<>(productList, pageable, total));

        // When
        ResponseEntity<Page<Product>> response = productSearchController.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
    }
    @Test
    public void testGetProductById() throws Exception {
        // Mocking a product object
        Product mockProduct = new Product("XDXD", "Mock Product", BigDecimal.valueOf(10.99));


        // Mocking the behavior of the ProductService to return the mockProduct when getProductById is called with ID 1
        when(productSearchService.getProductById("XDXD")).thenReturn(mockProduct);

        // Perform GET request to /products/{id} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Mock Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.99));
    }

}
