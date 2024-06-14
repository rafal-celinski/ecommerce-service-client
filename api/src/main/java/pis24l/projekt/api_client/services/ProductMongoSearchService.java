package pis24l.projekt.api_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductMongoSearchService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductMongoSearchService(ProductRepository productRepository, MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<Product> listCart(Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(ProductStatus.UP));
        query.with(pageable);

        List<Product> products = mongoTemplate.find(query, Product.class);
        long count = mongoTemplate.count(query, Product.class);

        return new PageImpl<>(products, pageable, count);
    }

    public Page<Product> listDone(Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").ne(ProductStatus.UP));
        query.with(pageable);

        List<Product> products = mongoTemplate.find(query, Product.class);
        long count = mongoTemplate.count(query, Product.class);

        return new PageImpl<>(products, pageable, count);

    }

}
