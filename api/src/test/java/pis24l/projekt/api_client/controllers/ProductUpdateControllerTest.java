package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.repositories.elastic.ProductSearchRepository;
import pis24l.projekt.api_client.repositories.mongo.CategoryRepository;
import pis24l.projekt.api_client.repositories.mongo.OrderResponseRepository;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductUpdateController.class)
public class ProductUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductSearchRepository productSearchRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private OrderResponseRepository orderResponseRepository;

    @InjectMocks
    private ProductUpdateController productUpdateController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductUpdateController(productRepository)).build();
    }

    @Test
    public void whenUpdateProductWithExistingId_thenProductIsUpdated() throws Exception {
        Product product = new Product("Laptop", BigDecimal.valueOf(1200.00), "Online", "Electronics", "Laptops", "High performance laptop",ProductStatus.UP);
        product.setId("XDXD");
        product.setStatus(ProductStatus.UP);
        given(productRepository.findById("XDXD")).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(put("/products/update/{id}", "XDXD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Laptop\"," +
                                "\"price\":1300.00," +
                                "\"location\":\"Store\"," +
                                "\"category\":\"Electronics\"," +
                                "\"subcategory\":\"Laptops\"," +
                                "\"description\":\"Updated description\"," +
                                "\"status\":\"UP\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Laptop"))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductWithNonExistingId_thenNotFound() throws Exception {
        given(productRepository.findById("XDXD")).willReturn(Optional.empty());

        mockMvc.perform(put("/products/update/{id}", "XDXD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\",\"price\":1200.00}"))
                .andExpect(status().isBadRequest());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductWithExistingIdButIncorrectData_thenProductIsNotUpdated() throws Exception {
        Product product = new Product("Laptop", BigDecimal.valueOf(1200.00), "Online", "Electronics", "Laptops", "High performance laptop", ProductStatus.UP);
        product.setId("XDXD");
        product.setStatus(ProductStatus.UP);
        given(productRepository.findById("XDXD")).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(put("/products/update/{id}", "XDXD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":-1300.00," +
                                "\"location\":\"Store\"," +
                                "\"category\":\"Electronics\"," +
                                "\"subcategory\":\"New_subcategory\"," +
                                "\"description\":\"Updated description\"," +
                                "\"status\":\"UP\"}"))
                .andExpect(status().isBadRequest());
    }

}
