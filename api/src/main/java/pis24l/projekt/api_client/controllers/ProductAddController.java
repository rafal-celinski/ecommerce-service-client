package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pis24l.projekt.api_client.model.Product;
import pis24l.projekt.api_client.repositories.elastic.ProductAddRepository;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductAddController {

    private final ProductRepository productRepository;
    private final ProductAddRepository productAddRepository;

    @Autowired
    public ProductAddController(ProductRepository productRepository, ProductAddRepository productAddRepository) {
        this.productRepository = productRepository;
        this.productAddRepository = productAddRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        Product savedProduct = productRepository.save(product);
        productAddRepository.save(savedProduct); // Save to Elasticsearch
        return ResponseEntity.ok(savedProduct);
    }
}
