package pis24l.projekt.api_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductSearchMongoService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductSearchMongoService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAllProducts() {
        Iterable<Product> productsIterable = productRepository.findAll();
        return StreamSupport.stream(productsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
}
