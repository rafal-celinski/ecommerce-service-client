package pis24l.projekt.api_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Category;
import pis24l.projekt.api_client.repositories.mongo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategorySearchService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategorySearchService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }
}
