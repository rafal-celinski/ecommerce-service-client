package pis24l.projekt.api_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.util.Optional;

@Service
public class ProductUpdateService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductUpdateService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProductStatus(String productId, String status) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStatus(status);
            productRepository.save(product);
        } else {
            System.out.println("Product with ID " + productId + " not found");
        }
    }
}
