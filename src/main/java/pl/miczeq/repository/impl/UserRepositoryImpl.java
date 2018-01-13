package pl.miczeq.repository.impl;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean save(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "INSERT INTO user(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL) VALUES(?, ?, ?, ?, ?)";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setString(5, user.getEmail());

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie dodac uzytkownika: " + user.getUsername() + ".");
            } else {
                throw new DatabaseException("Nie udalo sie dodac uzytkownika: " + user.getUsername() + ", SQL: " + SQL + ".");
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
    public boolean update(Long id, User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "UPDATE user SET USERNAME = ?, PASSWORD = ?, FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ? WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie zaktualizowac uzytkownika o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie zaktualizowac uzytkownika o ID: " + id + ", SQL: " + SQL + ".");
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
    public User findOne(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM user WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            User user = null;

            if (resultSet.next()) {
                user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden unikalny uzytkownik o ID: " + id);
            }

            return user;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);

        }
    }

    @Override
    public User findOne(String username) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM user WHERE USERNAME = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            User user = null;

            if (resultSet.next()) {
                user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden unikalny uzytkownik z Loginem: " + username);
            }

            return user;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(resultSet);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);

        }
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT * FROM user";

        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = ConnectionUtil.getResultSet(statement, SQL);

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL")));
            }

            return users;
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

        final String SQL = "DELETE FROM user WHERE ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            int executeUpdate = preparedStatement.executeUpdate();

            if (executeUpdate != 0) {
                System.out.println("Udalo sie usunac uzytkownika o ID: " + id + ".");
            } else {
                throw new DatabaseException("Nie udalo sie usunac uzytownika o ID: " + id + ".");
            }

            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }
}
