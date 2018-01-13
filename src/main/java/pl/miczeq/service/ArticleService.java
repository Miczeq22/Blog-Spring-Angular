package pl.miczeq.service;

import pl.miczeq.model.Article;

import java.util.List;

public interface ArticleService {
    boolean save(Article article);

    Article findOne(Long id);

    Article findOneByTitle(String title);

    List<Article> findAll();

    boolean remove(Long id);
}
