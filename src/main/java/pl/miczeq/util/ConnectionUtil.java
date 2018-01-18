package pl.miczeq.util;

import pl.miczeq.exception.DatabaseException;

import java.sql.*;

public final class ConnectionUtil {
    public static Connection getConnection() throws DatabaseException {
        final String URL = "jdbc:mysql://localhost:3306/blog?characterEncoding=UTF-8";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException("Nie udalo sie polaczyc z baza danych: " + URL + ".", e);
        }
    }

    public static ResultSet getResultSet(PreparedStatement preparedStatement) throws DatabaseException{
        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) {
                throw new DatabaseException("Nie udalo sie utworzyc resultSet: " + preparedStatement.toString() + ".");
            }

            return resultSet;
        } catch (SQLException e) {
            throw new DatabaseException("Nie udalo sie utworzyc resultSet, problem po stronie bazy danych.", e);
        }
    }

    public static ResultSet getResultSet(Statement statement, final String SQL) throws DatabaseException {
        try {
            ResultSet resultSet = statement.executeQuery(SQL);

            if (resultSet == null) {
                throw new DatabaseException("Nie udalo sie utworzyc resultSet: " + SQL + ".");
            }

            return resultSet;
        } catch (SQLException e) {
            throw new DatabaseException("Nie udalo sie utworzyc resultSet, problem po stronie bazy danych.", e);
        }
    }

    public static void close(ResultSet resultSet) throws DatabaseException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DatabaseException("Nie udalo sie zamknac resultSet", e);
            }
        }
    }

    public static void close(Statement statement) throws DatabaseException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DatabaseException("Nie udalo sie zamknac statement", e);
            }
        }
    }

    public static void close(PreparedStatement preparedStatement) throws DatabaseException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DatabaseException("Nie udalo sie zamknac preparedStatement", e);
            }
        }
    }

    public static void close(Connection connection) throws DatabaseException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseException("Nie udalo sie zamknac connection", e);
            }
        }
    }
}
