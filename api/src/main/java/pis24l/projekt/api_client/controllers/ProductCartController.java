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
import pis24l.projekt.api_client.services.ProductMongoSearchService;

import java.util.Optional;
@RestController
@RequestMapping("/cart")
public class ProductCartController {

    private final ProductElasticSearchService productElasticSearchService;
    private final ProductRepository productRepository;
    private final ProductMongoSearchService productMongoSearchService;

    @Autowired
    public ProductCartController(ProductElasticSearchService productElasticSearchService, ProductRepository productRepository, ProductMongoSearchService productMongoSearchService) {
        this.productElasticSearchService = productElasticSearchService;
        this.productRepository = productRepository;
        this.productMongoSearchService = productMongoSearchService;
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

    @GetMapping("/list-all")
    public ResponseEntity<Page<Product>> listCart(
            Pageable pageable) {
        Page<Product> products = productMongoSearchService.listCart(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/archive")
    public ResponseEntity<Page<Product>> listArchive(Pageable pageable)
    {
        Page<Product> products = productMongoSearchService.listDone(pageable);
        return ResponseEntity.ok(products);
    }
}
