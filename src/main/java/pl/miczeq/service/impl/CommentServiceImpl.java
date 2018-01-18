package pl.miczeq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miczeq.exception.BadCommentException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Comment;
import pl.miczeq.repository.CommentRepository;
import pl.miczeq.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean save(Comment comment) throws BadCommentException, DatabaseException {
        boolean result;

        if (comment.getId() != null) {
            result = commentRepository.update(comment.getId(), comment);
        } else {
            result = commentRepository.save(comment);
        }

        return result;
    }

    @Override
    public Comment findOne(Long id) throws DatabaseException {
        return commentRepository.findOne(id);
    }

    @Override
    public List<Comment> findAllForArticle(Long id) throws DatabaseException {
        return commentRepository.findAllForArticle(id);
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        return commentRepository.remove(id);
    }
}
