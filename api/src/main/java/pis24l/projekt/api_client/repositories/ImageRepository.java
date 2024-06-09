package pis24l.projekt.api_client.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pis24l.projekt.api_client.models.Image;

import java.util.List;
@Repository
public interface ImageRepository extends MongoRepository<Image, Long> {
    List<Image> findByProductId(String productId);

}
