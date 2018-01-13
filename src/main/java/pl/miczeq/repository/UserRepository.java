package pl.miczeq.repository;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;

import java.util.List;

public interface UserRepository {
    boolean save(User user) throws DatabaseException;

    boolean update(Long id, User user) throws DatabaseException;

    User findOne(Long id);

    User findOne(String username);

    List<User> findAll() throws DatabaseException;

    boolean remove(Long id);
}
