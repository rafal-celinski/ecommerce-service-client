package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;
import pis24l.projekt.api_client.services.ProductElasticSearchService;
import pis24l.projekt.api_client.services.ProductSearchMongoService;

import java.util.Optional;
@RestController
@RequestMapping("/cart")
public class ProductCartController {

    private final ProductElasticSearchService productElasticSearchService;
    private final ProductRepository productRepository;
    private final ProductSearchMongoService productSearchMongoService;

    @Autowired
    public ProductCartController(ProductElasticSearchService productElasticSearchService, ProductRepository productRepository, ProductSearchMongoService productSearchMongoService) {
        this.productElasticSearchService = productElasticSearchService;
        this.productRepository = productRepository;
        this.productSearchMongoService = productSearchMongoService;
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

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchCart(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) ProductStatus status,
            Pageable pageable) {
        Page<Product> products = productSearchMongoService.searchProducts(search, category, subcategory, location, status, pageable);
        return ResponseEntity.ok(products);
    }
}
