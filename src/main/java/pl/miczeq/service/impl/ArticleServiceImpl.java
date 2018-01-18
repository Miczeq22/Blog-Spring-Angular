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
    public boolean save(Article article) throws BadArticleException, DatabaseException {
        boolean result;

        if (article.getId() != null) {
            result = articleRepository.update(article.getId(), article);
        } else {
            result = articleRepository.save(article);
        }

        return result;
    }

    @Override
    public Article findOne(Long id) throws DatabaseException {
        return articleRepository.findOne(id);
    }

    @Override
    public Article findOneByTitle(String title) throws DatabaseException {
        return articleRepository.findOneByTitle(title);
    }

    @Override
    public List<Article> findAll() throws DatabaseException {
        return articleRepository.findAll();
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        return articleRepository.remove(id);
    }
}
