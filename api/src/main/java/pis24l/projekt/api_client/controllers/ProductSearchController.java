package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.model.Product;
import pis24l.projekt.api_client.model.ProductSearchRequest;
import pis24l.projekt.api_client.service.ProductSearchService;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @Autowired
    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productSearchService.getProductById(id);
        return ResponseEntity.ok(product);
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
        Page<Product> products = productSearchService.searchProducts(search, category, subcategory, minPrice, maxPrice, location, pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search/full-text")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody ProductSearchRequest searchRequest) {
        List<Product> products = productSearchService.searchProductsFullText(searchRequest.getQuery());
        return ResponseEntity.ok(products);
    }
}
