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
    public boolean save(Comment comment) {
        boolean result;

        try {
            if (comment.getId() != null) {
                result = commentRepository.update(comment.getId(), comment);
            } else {
                result = commentRepository.save(comment);
            }
        } catch (BadCommentException | DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Comment findOne(Long id) {
        Comment comment;

        try {
            comment = commentRepository.findOne(id);
        } catch (DatabaseException e) {
            comment = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return comment;
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments;

        try {
            comments = commentRepository.findAll();
        } catch (DatabaseException e) {
            comments = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return comments;
    }

    @Override
    public boolean remove(Long id) {
        boolean result;

        try {
            result = commentRepository.remove(id);
        } catch (DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
