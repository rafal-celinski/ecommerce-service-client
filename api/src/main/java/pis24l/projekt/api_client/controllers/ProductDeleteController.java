package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/products")
public class ProductDeleteController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDeleteController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
