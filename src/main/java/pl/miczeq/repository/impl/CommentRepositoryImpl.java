package pl.miczeq.repository.impl;

import org.springframework.stereotype.Repository;
import pl.miczeq.exception.BadCommentException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Comment;
import pl.miczeq.repository.CommentRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @Override
    public boolean save(Comment comment) throws BadCommentException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "INSERT INTO comment(USER_ID, ARTICLE_ID, TITLE, CONTENT, LIKES, LAST_UPDATE_DATE) VALUES(?, ?, ?, ?, ?, ?)";

        validateComment(comment);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, comment.getUserId());
            preparedStatement.setLong(2, comment.getArticleId());
            preparedStatement.setString(3, comment.getTitle());
            preparedStatement.setString(4, comment.getContent());
            preparedStatement.setInt(5, comment.getLikes());
            preparedStatement.setDate(6, comment.getLastUpdateDate());

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie dodac komentarz: " + comment.getTitle() + ".");
            } else {
                throw new DatabaseException("Nie udalo sie dodac komentarza: " + comment.getTitle() + ".");
            }

            return executeUpdate > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public boolean update(Long id, Comment comment) throws BadCommentException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "UPDATE comment SET USER_ID = ?, ARTICLE_ID = ?, TITLE = ?, CONTENT = ?, LIKES = ?, LAST_UPDATE_DATE = ? WHERE ID = ?";

        validateComment(comment);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, comment.getUserId());
            preparedStatement.setLong(2, comment.getArticleId());
            preparedStatement.setString(3, comment.getTitle());
            preparedStatement.setString(4, comment.getContent());
            preparedStatement.setInt(5, comment.getLikes());
            preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
            preparedStatement.setLong(7, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie zaktualizowac komentarz o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie zaktualizowac komentarza: " + id + ".");
            }

            return executeUpdate > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public Comment findOne(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM comment WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            Comment comment = null;

            if (resultSet.next()) {
                comment = new Comment(resultSet.getLong("ID"), resultSet.getLong("USER_ID"), resultSet.getLong("ARTICLE_ID"),
                        resultSet.getString("TITLE"), resultSet.getString("CONTENT"), resultSet.getInt("LIKES"), resultSet.getDate("LAST_UPDATE_DATE"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden komentarz o unikalnym ID: " + id + ".");
            }

            return comment;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public List<Comment> findAllForArticle(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM comment WHERE ARTICLE_ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            List<Comment> comments = new ArrayList<>();

            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getLong("ID"), resultSet.getLong("USER_ID"), resultSet.getLong("ARTICLE_ID"),
                        resultSet.getString("TITLE"), resultSet.getString("CONTENT"), resultSet.getInt("LIKES"), resultSet.getDate("LAST_UPDATE_DATE")));
            }

            return comments;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "DELETE FROM comment WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie ususnac komentarz o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie ususnac komentarza o ID: " + id + ".");
            }

            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    private void validateComment(Comment comment) throws BadCommentException {
        if (comment == null) {
            throw new BadCommentException("Komentarz nie moze byc nullem.");
        }

        if (isEmptyString(comment.getTitle())) {
            throw new BadCommentException("Tytul nie moze byc pusty");
        }

        if (isEmptyString(comment.getContent())) {
            throw new BadCommentException("Tresc nie moze byc pusta");
        }
    }

    private boolean isEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }

        return false;
    }
}
