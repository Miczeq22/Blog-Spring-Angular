package pl.miczeq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miczeq.exception.BadArticleException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Article;
import pl.miczeq.repository.ArticleRepository;
import pl.miczeq.service.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public boolean save(Article article) {
        boolean result;

        try {
            if (article.getId() != null) {
                result = articleRepository.update(article.getId(), article);
            } else {
                result = articleRepository.save(article);
            }
        } catch (BadArticleException | DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Article findOne(Long id) {
        Article article;

        try {
            article = articleRepository.findOne(id);
        } catch (DatabaseException e) {
            article = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return article;
    }

    @Override
    public Article findOneByTitle(String title) {
        Article article;

        try {
            article = articleRepository.findOneByTitle(title);
        } catch (DatabaseException e) {
            article = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return article;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles;

        try {
            articles = articleRepository.findAll();
        } catch (DatabaseException e) {
            articles = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return articles;
    }

    @Override
    public boolean remove(Long id) {
        boolean result;

        try {
            result = articleRepository.remove(id);
        } catch (DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
