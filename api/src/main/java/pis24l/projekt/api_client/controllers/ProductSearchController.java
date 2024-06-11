package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.services.ProductElasticSearchService;
import pis24l.projekt.api_client.services.ProductSearchMongoService;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductSearchMongoService productSearchMongoService;
    private final ProductElasticSearchService productElasticSearchService;

    @Autowired
    public ProductSearchController(ProductSearchMongoService productSearchMongoService, ProductElasticSearchService productElasticSearchService) {
        this.productSearchMongoService = productSearchMongoService;
        this.productElasticSearchService = productElasticSearchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productSearchMongoService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String location,
            Pageable pageable) {
        Page<Product> products = productElasticSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);
        return ResponseEntity.ok(products);
    }

}
