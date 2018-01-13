package pl.miczeq.repository.impl;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            boolean result;

            if (executeUpdate != 0) {
                System.out.println("Udalo sie dodac uzytkownika: " + user.getUsername() + ".");
                result = true;
            } else {
                throw new DatabaseException("Nie udalo sie dodac uzytkownika: " + user.getUsername() + ", SQL: " + SQL + ".");
            }

            return result;

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

            boolean result;

            if (executeUpdate != 0) {
                System.out.println("Udalo sie zaktualizowac uzytkownika o ID: " + id + ".");
                result = true;
            } else {
                throw new DatabaseException("Nie udalo sie zaktualizowac uzytkownika o ID: " + id + ", SQL: " + SQL + ".");
            }

            return result;

        } catch (SQLException e) {
            throw new DatabaseException("Problem po stronie bazy danych.", e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
    }

    @Override
    public User findOne(Long id) {
        return null;
    }

    @Override
    public User findOne(String username) {
        return null;
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return null;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }
}
