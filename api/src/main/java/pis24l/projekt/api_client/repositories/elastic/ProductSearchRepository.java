package pis24l.projekt.api_client.repositories.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import pis24l.projekt.api_client.models.Product;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByTitleContainingOrDescriptionContaining(String title, String description);
}
