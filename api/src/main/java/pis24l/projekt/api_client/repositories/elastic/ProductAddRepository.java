package pis24l.projekt.api_client.repositories.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import pis24l.projekt.api_client.model.Product;

import java.util.List;

@Repository
public interface ProductAddRepository extends ElasticsearchRepository<Product, Long> {
    List<Product> findByTitleContainingOrDescriptionContaining(String title, String description);
}
