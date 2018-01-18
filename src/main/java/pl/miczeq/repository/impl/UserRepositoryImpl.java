package pl.miczeq.repository.impl;

import org.springframework.stereotype.Repository;
import pl.miczeq.exception.BadUserException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Role;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean save(User user) throws BadUserException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "INSERT INTO user(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ROLE_ID) VALUES(?, ?, ?, ?, ?, 2)";

        validateUser(user);

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
    public boolean update(Long id, User user) throws BadUserException, DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String SQL = "UPDATE user SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?";

        validateUser(user);

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setLong(3, id);

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

        final String SQL = "SELECT u.ID, u.USERNAME, u.PASSWORD, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, r.ID AS ROLE_ID, r.ROLE_NAME FROM user u" +
                " INNER JOIN role r ON (u.ROLE_ID = r.ID) WHERE u.ID = ?";

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            User user = null;

            if (resultSet.next()) {
                user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
                user.getRoles().add(new Role(resultSet.getLong("ROLE_ID"), resultSet.getString("ROLE_NAME")));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden unikalny uzytkownik o ID: " + id);
            }

            if (user == null) {
                throw new DatabaseException("W bazie nie istnieje uzytkownik o ID: " + id + ".");
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
    public User findOneByUsername(String username) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT u.ID, u.USERNAME, u.PASSWORD, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, r.ID AS ROLE_ID, r.ROLE_NAME FROM user u" +
                " INNER JOIN role r ON (u.ROLE_ID = r.ID) WHERE USERNAME = ?";

        if (username == null || username.isEmpty()) {
            throw new DatabaseException("Podany login jest pusty.");
        }

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            User user = null;

            if (resultSet.next()) {
                user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
                user.getRoles().add(new Role(resultSet.getLong("ROLE_ID"), resultSet.getString("ROLE_NAME")));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden unikalny uzytkownik z Loginem: " + username + ".");
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
    public User findOneByEmail(String email) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String SQL = "SELECT u.ID, u.USERNAME, u.PASSWORD, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, r.ID AS ROLE_ID, r.ROLE_NAME FROM user u" +
                " INNER JOIN role r ON (u.ROLE_ID = r.ID) WHERE EMAIL = ?";

        if (email == null || email.isEmpty()) {
            throw new DatabaseException("Podany email jest pusty.");
        }

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, email);

            resultSet = ConnectionUtil.getResultSet(preparedStatement);

            User user = null;

            if (resultSet.next()) {
                user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
                user.getRoles().add(new Role(resultSet.getLong("ROLE_ID"), resultSet.getString("ROLE_NAME")));
            }

            if (resultSet.next()) {
                throw new DatabaseException("Istnieje wiecej niz jeden unikalny uzytkownik z emailem: " + email + ".");
            }

            if (user == null) {
                throw new DatabaseException("W bazie nie istnieje uzytkownik o emailu: " + email + ".");
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

        final String SQL = "SELECT u.ID, u.USERNAME, u.PASSWORD, u.FIRST_NAME, u.LAST_NAME, u.EMAIL, r.ID AS ROLE_ID, r.ROLE_NAME FROM user u" +
                " INNER JOIN role r ON (u.ROLE_ID = r.ID)";

        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.createStatement();
            resultSet = ConnectionUtil.getResultSet(statement, SQL);

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(resultSet.getLong("ID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"));
                user.getRoles().add(new Role(resultSet.getLong("ROLE_ID"), resultSet.getString("ROLE_NAME")));
                users.add(user);
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

    private void validateUser(User user) throws DatabaseException, BadUserException {
        if (user == null) {
            throw new BadUserException("Uzytkownik jest nullem.");
        }

        if (isEmptyString(user.getUsername())) {
            throw new BadUserException("Login nie moze byc pusty.");
        }

        if (isEmptyString(user.getEmail())) {
            throw new BadUserException("Email nie moze byc pusty.");
        }

        if (isEmptyString(user.getPassword())) {
            throw new BadUserException("Haslo nie moze byc puste.");
        }

        if (isEmptyString(user.getFirstname())) {
            throw new BadUserException("Imie nie moze byc puste.");
        }

        if (user.getId() == null && findOneByUsername(user.getUsername()) != null) {
            throw new BadUserException("Uzytkownik o takim loginie: " + user.getUsername() + " juz istnieje w bazie.");
        }

        if (user.getId() == null && findOneByEmail(user.getEmail()) != null) {
            throw new BadUserException("Uzytkownik o takim emailu: " + user.getEmail() + " juz istnieje w bazie.");
        }
    }

    private boolean isEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }

        return false;
    }
}
