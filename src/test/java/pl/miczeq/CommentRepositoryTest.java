package pl.miczeq;

import org.junit.Before;
import org.junit.Test;
import pl.miczeq.exception.BadCommentException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Comment;
import pl.miczeq.repository.CommentRepository;
import pl.miczeq.repository.impl.CommentRepositoryImpl;

import java.sql.Date;

public class CommentRepositoryTest {
    private CommentRepository commentRepository;

    @Before
    public void init() {
        commentRepository = new CommentRepositoryImpl();
    }

    @Test
    public void saveTest() {
        try {
            commentRepository.save(new Comment(2L, 1L, "Komentarz", "Tresc komentarza", 2, new Date(System.currentTimeMillis())));
        } catch (BadCommentException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            commentRepository.update(1L, new Comment(2L, 1L, "komentarz", "Tresc komentarza", 2, new Date(System.currentTimeMillis())));
        } catch (BadCommentException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneTest() {
        try {
            System.out.println(commentRepository.findOne(1L));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() {
        try {
            commentRepository.findAll().forEach(comment -> System.out.println(comment));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeTest() {
        try {
            System.out.println(commentRepository.remove(2L));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
