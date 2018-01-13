package pl.miczeq;

import org.junit.Before;
import org.junit.Test;
import pl.miczeq.exception.BadArticleException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Article;
import pl.miczeq.repository.ArticleRepository;
import pl.miczeq.repository.impl.ArticleRepositoryImpl;

import java.sql.Date;

public class ArticleRepositoryTest {
    private ArticleRepository articleRepository;

    @Before
    public void init() {
        articleRepository = new ArticleRepositoryImpl();
    }

    @Test
    public void saveTest() {
        try {
            articleRepository.save(new Article("Tytul 2", "Tresc 2...", 2L, 5, new Date(System.currentTimeMillis()), "url 2"));
        } catch (BadArticleException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            articleRepository.update(2L, new Article("tytul 2", "tresc 2...", 2L, 5, new Date(System.currentTimeMillis()), "url 2"));
        } catch (BadArticleException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneTest() {
        try {
            System.out.println(articleRepository.findOne(1L));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneByTitleTest() {
        try {
            System.out.println(articleRepository.findOneByTitle("Artykul 1"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllTest() {
        try {
            articleRepository.findAll().forEach(article -> System.out.println(article));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeTest() {
        try {
            articleRepository.remove(2L);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
