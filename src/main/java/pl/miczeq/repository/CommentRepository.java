package pl.miczeq.repository;

import pl.miczeq.exception.BadCommentException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Comment;

import java.util.List;

public interface CommentRepository {
    boolean save(Comment comment) throws BadCommentException, DatabaseException;

    boolean update(Long id, Comment comment) throws BadCommentException, DatabaseException;

    Comment findOne(Long id) throws DatabaseException;

    List<Comment> findAllForArticle(Long id) throws DatabaseException;

    boolean remove(Long id) throws DatabaseException;
}
