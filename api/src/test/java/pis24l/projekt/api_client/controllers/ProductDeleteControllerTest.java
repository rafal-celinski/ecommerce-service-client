package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import pis24l.projekt.api_client.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductDeleteController.class)
public class ProductDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void deleteProduct_WhenProductExists_ShouldReturnNoContent() throws Exception {
        String productId = "XDXD";
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        String productId = "XDXD";
        when(productRepository.existsById(productId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }
}
