package pl.miczeq.repository.impl;

import org.springframework.stereotype.Repository;
import pl.miczeq.exception.BadArticleException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Article;
import pl.miczeq.repository.ArticleRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    @Override
    public boolean save(Article article) throws BadArticleException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "INSERT INTO article(TITLE, CONTENT, USER_ID, LIKES, LAST_UPDATE_DATE, IMG_URL) VALUES (?, ?, ?, ?, ?, ?)";

        validateArticle(article);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setLong(3, article.getUserId());
            preparedStatement.setInt(4, article.getLikes());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
            preparedStatement.setString(6, article.getImgUrl());

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie dodac artykul: " + article.getTitle() + ".");
            } else {
                throw new DatabaseException("Nie udalo sie dodac artykulu: " + article.getTitle() + ".");
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
    public boolean update(Long id, Article article) throws BadArticleException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "UPDATE article SET TITLE = ?, CONTENT = ?, USER_ID = ?, LIKES = ?, LAST_UPDATE_DATE = ?, IMG_URL = ? WHERE ID = ?";

        validateArticle(article);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setLong(3, article.getUserId());
            preparedStatement.setInt(4, article.getLikes());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
            preparedStatement.setString(6, article.getImgUrl());
            preparedStatement.setLong(7, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie zaktualizowac artykul o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie zaktualizowac artykulu: " + id + ".");
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
    public Article findOne(Long id) throws DatabaseException {
       Connection connection = null;
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;

        final String SQL = "SELECT * FROM article WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            Article article = null;

            if (resultSet.next()) {
                article = new Article(resultSet.getLong("ID"), resultSet.getString("TITLE"), resultSet.getString("CONTENT"),
                        resultSet.getLong("USER_ID"), resultSet.getInt("LIKES"), resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getString("IMG_URL"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden artykul o unikalnym ID: " + id + ".");
            }

            return article;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public Article findOneByTitle(String title) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM article WHERE TITLE = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, title);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            Article article = null;

            if (resultSet.next()) {
                article = new Article(resultSet.getLong("ID"), resultSet.getString("TITLE"), resultSet.getString("CONTENT"),
                        resultSet.getLong("USER_ID"), resultSet.getInt("LIKES"), resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getString("IMG_URL"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden artykul o unikalnym tytule: " + title + ".");
            }

            return article;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public List<Article> findAll() throws DatabaseException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM article";

        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = ConnectionUtil.getResultSet(statement, SQL);

            List<Article> articles = new ArrayList<>();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getLong("ID"), resultSet.getString("TITLE"), resultSet.getString("CONTENT"),
                        resultSet.getLong("USER_ID"), resultSet.getInt("LIKES"), resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getString("IMG_URL")));
            }

            return articles;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "DELETE FROM article WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie usunac artykul o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie usunac artykulu o ID: " + id + ".");
            }

            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.");
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    private void validateArticle(Article article) throws BadArticleException, DatabaseException {
        if (article == null) {
            throw new BadArticleException("Artykul nie moze byc nullem.");
        }

        if (isEmptyString(article.getTitle())) {
            throw new BadArticleException("Tytul nie moze byc pusty.");
        }

        if (isEmptyString(article.getContent())) {
            throw new BadArticleException("Zawartosc nie moze byc pusty.");
        }

        if (findOneByTitle(article.getTitle()) != null) {
            throw new BadArticleException("Artykul z tytulem: " + article.getTitle() + " juz istnieje w bazie.");
        }
    }

    private boolean isEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }

        return false;
    }
}
