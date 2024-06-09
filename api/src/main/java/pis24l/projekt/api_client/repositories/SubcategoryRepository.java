package pis24l.projekt.api_client.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pis24l.projekt.api_client.model.Subcategory;

import java.util.List;

@Repository
public interface SubcategoryRepository extends MongoRepository<Subcategory, Long> {
    List<Subcategory> findSubcategoriesByCategoryId(Long categoryId);
}
