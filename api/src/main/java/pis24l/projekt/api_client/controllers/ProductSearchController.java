package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.services.ProductElasticSearchService;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductElasticSearchService productElasticSearchService;

    @Autowired
    public ProductSearchController(ProductElasticSearchService productElasticSearchService) {
        this.productElasticSearchService = productElasticSearchService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Optional<Product> product = productElasticSearchService.getProductById(id);
        if (product.isPresent()) { return ResponseEntity.ok(product.get());}
        else return ResponseEntity.notFound().build();
    }
}
