package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ProductAddControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductAddController productAddController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productAddController).build();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void whenPostRequestToProductsAndValidProduct_thenCorrectResponse() throws Exception {
        Product product = new Product("Laptop", BigDecimal.valueOf(999.99), "Warsaw", "XD", "XD", "High performance laptop with latest specifications");
        given(productRepository.save(any(Product.class))).willReturn(product);

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\"," +
                                "\"price\":999.99," +
                                "\"location\": \"Warsaw\"," +
                                "\"category\": 1," +
                                "\"subcategory\": 1," +
                                "\"description\": \"High performance laptop with latest specifications\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Laptop"));
    }

    @Test
    public void whenPostRequestToProductsAndInvalidProduct_thenBadRequest() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\"," +
                                "\"price\":999.99," +
                                "\"location\": \"Warsaw\"," +
                                "\"category\": 1," +
                                "\"description\": \"High performance laptop with latest specifications\"}"))
                .andExpect(status().isBadRequest());
    }
}
