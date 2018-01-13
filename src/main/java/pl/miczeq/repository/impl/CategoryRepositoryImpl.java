package pl.miczeq.repository.impl;

import pl.miczeq.exception.BadCategoryException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Category;
import pl.miczeq.repository.CategoryRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public boolean save(Category category) throws BadCategoryException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "INSERT INTO category(CATEGORY_NAME) VALUES (?)";

        validateCategory(category);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, category.getCategoryName());

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie dodac kategorie: " + category.getCategoryName() + ".");
            } else {
                throw new DatabaseException("Nie udalo sie dodac kategorii: " + category.getCategoryName() + ".");
            }

            return executeUpdate > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public boolean update(Long id, Category category) throws BadCategoryException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "UPDATE category SET CATEGORY_NAME = ? WHERE ID = ?";

        validateCategory(category);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setLong(2, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie zaktualizowac kategorie o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie zaktualizowac kategorii o ID: " + id + ".");
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
    public Category findOne(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM category WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            Category category = null;

            if (resultSet.next()) {
                category = new Category(resultSet.getLong("ID"), resultSet.getString("CATEGORY_NAME"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jedna kategoria o unikalnym ID: " + id + ".");
            }

            return category;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public Category findOneByName(String name) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM category WHERE CATEGORY_NAME = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            Category category = null;

            if (resultSet.next()) {
                category = new Category(resultSet.getLong("ID"), resultSet.getString("CATEGORY_NAME"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jedna kategoria o unikalej nazwie: " + name + ".");
            }

            return category;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public List<Category> findAll() throws DatabaseException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM category";

        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = ConnectionUtil.getResultSet(statement, SQL);

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getLong("ID"), resultSet.getString("CATEGORY_NAME")));
            }

            return categories;
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

        final String SQL = "DELETE FROM category WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie usunac kategorie o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie usunac kategorii od ID: " + id + ".");
            }

            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    private void validateCategory(Category category) throws BadCategoryException, DatabaseException {
        if (category == null) {
            throw new BadCategoryException("Kategoria nie moze byc nullem.");
        }

        if (isEmptyString(category.getCategoryName())) {
            throw new BadCategoryException("Nazwa kategorii nie moze byc pusta.");
        }

        if (findOneByName(category.getCategoryName()) != null) {
            throw new BadCategoryException("Kategoria o-" +
                    " nazwie: " + category.getCategoryName() + " jest juz w bazie.");
        }
    }

    private boolean isEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }

        return false;
    }
}
