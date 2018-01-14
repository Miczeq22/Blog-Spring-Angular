package pl.miczeq.service;

import pl.miczeq.model.Comment;

import java.util.List;

public interface CommentService {
    boolean save(Comment comment);

    Comment findOne(Long id);

    List<Comment> findAllForArticle(Long id);

    boolean remove(Long id);
}
