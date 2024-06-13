package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductDeleteController.class)
public class ProductDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Configuration
    static class TestConfig {
        @Bean
        public MongoTemplate mongoTemplate() {
            return Mockito.mock(MongoTemplate.class);
        }

        @Bean
        public ProductDeleteController productDeleteController(ProductRepository productRepository) {
            return new ProductDeleteController(productRepository);
        }
    }


    @Test
    public void deleteProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Given
        String productId = "nonExistingProductId";
        when(productRepository.existsById(productId)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/products/delete/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteProduct_WhenProductExists_ShouldReturnNoContent() throws Exception {
        // Given
        String productId = "existingProductId";
        when(productRepository.existsById(productId)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/products/delete/{id}", productId))
                .andExpect(status().isNoContent());
    }
}
