package pis24l.projekt.api_client.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pis24l.projekt.api_client.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Long> {

}
