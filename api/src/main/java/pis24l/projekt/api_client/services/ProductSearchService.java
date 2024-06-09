package pis24l.projekt.api_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.ImageRepository;
import pis24l.projekt.api_client.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSearchService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductSearchService(ProductRepository productRepository, ImageRepository imageRepository, MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<Product> searchProducts(String search, Long category, Long subcategory, BigDecimal minPrice, BigDecimal maxPrice, String location, Pageable pageable) {
        Query query = new Query();

        if (search != null && !search.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(search, "i"));
        }
        if (category != null) {
            query.addCriteria(Criteria.where("category").is(category));
        }
        if (subcategory != null) {
            query.addCriteria(Criteria.where("subcategory").is(subcategory));
        }
        if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        }
        if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }
        if (location != null && !location.isEmpty()) {
            query.addCriteria(Criteria.where("location").regex(location, "i"));
        }

        query.with(pageable);

        List<Product> products = mongoTemplate.find(query, Product.class);
        long count = mongoTemplate.count(query, Product.class);

        return new PageImpl<>(products, pageable, count);
    }

    public Product getProductById(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    }
}