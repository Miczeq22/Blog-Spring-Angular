package pl.miczeq.service;

import pl.miczeq.exception.BadArticleException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Article;

import java.util.List;

public interface ArticleService {
    boolean save(Article article) throws BadArticleException, DatabaseException;

    Article findOne(Long id) throws DatabaseException;

    Article findOneByTitle(String title) throws DatabaseException;

    List<Article> findAll() throws DatabaseException;

    boolean remove(Long id) throws DatabaseException;
}
