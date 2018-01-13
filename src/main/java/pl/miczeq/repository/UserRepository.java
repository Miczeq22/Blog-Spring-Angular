package pl.miczeq.repository;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;

import java.util.List;

public interface UserRepository {
    boolean save(User user) throws DatabaseException;

    boolean update(Long id, User user) throws DatabaseException;

    User findOne(Long id) throws DatabaseException;

    User findOne(String username) throws DatabaseException;

    List<User> findAll() throws DatabaseException;

    boolean remove(Long id) throws DatabaseException;
}
