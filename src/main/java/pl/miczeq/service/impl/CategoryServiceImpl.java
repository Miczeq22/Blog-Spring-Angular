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
    public boolean save(Category category) {
        boolean result;

        try {
            if (category.getId() != null) {
                result = categoryRepository.update(category.getId(), category);
            } else {
                result = categoryRepository.save(category);
            }
        } catch (BadCategoryException | DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Category findOne(Long id) {
        Category category;

        try {
            category = categoryRepository.findOne(id);
        } catch (DatabaseException e) {
            category = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public Category findOneByName(String name) {
        Category category;

        try {
            category = categoryRepository.findOneByName(name);
        } catch (DatabaseException e) {
            category = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories;

        try {
            categories = categoryRepository.findAll();
        } catch (DatabaseException e) {
            categories = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public boolean remove(Long id) {
        boolean result;

        try {
            result = categoryRepository.remove(id);
        } catch (DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
