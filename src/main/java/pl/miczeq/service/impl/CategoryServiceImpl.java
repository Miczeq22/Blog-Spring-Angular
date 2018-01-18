package pl.miczeq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miczeq.exception.BadCategoryException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Category;
import pl.miczeq.repository.CategoryRepository;
import pl.miczeq.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean save(Category category) throws BadCategoryException, DatabaseException {
        boolean result;

        if (category.getId() != null) {
            result = categoryRepository.update(category.getId(), category);
        } else {
            result = categoryRepository.save(category);
        }

        return result;
    }

    @Override
    public Category findOne(Long id) throws DatabaseException {
        return categoryRepository.findOne(id);
    }

    @Override
    public Category findOneByName(String name) throws DatabaseException {
        return categoryRepository.findOneByName(name);
    }

    @Override
    public List<Category> findAll() throws DatabaseException {
        return categoryRepository.findAll();
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        return categoryRepository.remove(id);
    }
}
