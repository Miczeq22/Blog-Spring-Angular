package pl.miczeq.service;

import pl.miczeq.exception.BadCategoryException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Category;

import java.util.List;

public interface CategoryService {
    boolean save(Category category) throws BadCategoryException, DatabaseException;

    Category findOne(Long id) throws DatabaseException;

    Category findOneByName(String name) throws DatabaseException;

    List<Category> findAll() throws DatabaseException;

    boolean remove(Long id) throws DatabaseException;
}
