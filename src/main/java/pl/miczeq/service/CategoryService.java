package pl.miczeq.service;

import pl.miczeq.model.Category;

import java.util.List;

public interface CategoryService {
    boolean save(Category category);

    Category findOne(Long id);

    Category findOneByName(String name);

    List<Category> findAll();

    boolean remove(Long id);
}
