package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pis24l.projekt.api_client.repositories.ProductRepository;
import pis24l.projekt.api_client.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductUpdateController.class)
public class ProductUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void whenUpdateProductWithExistingId_thenProductIsUpdated() throws Exception {
        Product product = new Product("Laptop", BigDecimal.valueOf(1200.00), "Online", 1L, 1L, "High performance laptop");
        product.setId("XDXD");
        given(productRepository.findById("XDXD")).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(put("/products/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Laptop\"," +
                                "\"price\":1300.00," +
                                "\"location\":\"Store\"," +
                                "\"category\":1," +
                                "\"subcategory\":1," +
                                "\"description\":\"Updated description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Laptop"))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductWithNonExistingId_thenNotFound() throws Exception {
        given(productRepository.findById("XDXD")).willReturn(Optional.empty());

        mockMvc.perform(put("/products/update/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\",\"price\":1200.00}"))
                .andExpect(status().isBadRequest());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductWithExistingIdButIncorrectData_thenProductIsNotUpdated() throws Exception {
        Product product = new Product("Laptop", BigDecimal.valueOf(1200.00), "Online", 1L, 1L, "High performance laptop");
        product.setId("XDXD");
        given(productRepository.findById("XDXD")).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(put("/products/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Laptop\"," +
                                "\"price\":1300.00," +
                                "\"location\":\"Store\"," +
                                "\"category\":1," +
                                "\"subcategory\":\"New_subcategory\"," +
                                "\"description\":\"Updated description\"}"))
                .andExpect(status().isBadRequest());
    }
}
