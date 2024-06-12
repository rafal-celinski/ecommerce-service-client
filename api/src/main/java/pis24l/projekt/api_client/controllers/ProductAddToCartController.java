package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;
import pis24l.projekt.api_client.services.ProductElasticSearchService;

import java.io.IOException;
import java.util.Optional;
@RestController
@RequestMapping("/api/cart")
public class ProductAddToCartController {

    private final ProductElasticSearchService productElasticSearchService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductAddToCartController(ProductElasticSearchService productElasticSearchService, ProductRepository productRepository) {
        this.productElasticSearchService = productElasticSearchService;
        this.productRepository = productRepository;
    }

    @GetMapping("/add/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable String productId) {
        try {
            Optional<Product> productOpt = productElasticSearchService.getProductById(productId);
            if (productOpt.isPresent()) {
                productRepository.save(productOpt.get());
                return ResponseEntity.ok("Product added to cart successfully");
            } else {
                return ResponseEntity.status(404).body("Product not found in Elasticsearch");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching product from Elasticsearch: " + e.getMessage());
        }
    }
}
