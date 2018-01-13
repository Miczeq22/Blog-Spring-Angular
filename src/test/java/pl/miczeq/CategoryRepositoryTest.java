package pl.miczeq;

import org.junit.Before;
import org.junit.Test;
import pl.miczeq.exception.BadCategoryException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Category;
import pl.miczeq.repository.CategoryRepository;
import pl.miczeq.repository.impl.CategoryRepositoryImpl;

public class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;

    @Before
    public void init() {
        categoryRepository = new CategoryRepositoryImpl();
    }

    @Test
    public void saveTest() {
        try {
            categoryRepository.save(new Category("Samochody"));
        } catch (BadCategoryException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            categoryRepository.update(1L, new Category("Silnik"));
        } catch (BadCategoryException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneTest() {
        try {
            System.out.println(categoryRepository.findOne(1L));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneByNameTest() {
        try {
            System.out.println(categoryRepository.findOneByName("Silnik"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllTest() {
        try {
            categoryRepository.findAll().forEach(category -> System.out.println(category));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeTest() {
        try {
            categoryRepository.remove(3L);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
