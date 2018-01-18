package pl.miczeq.service;

import pl.miczeq.exception.BadUserException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;

import java.util.List;

public interface UserService {
    boolean save(User user) throws BadUserException, DatabaseException;

    User findOne(Long id) throws DatabaseException;

    User findOneByUsername(String username) throws DatabaseException;

    User findOneByEmail(String email) throws DatabaseException;

    List<User> findAll() throws DatabaseException;

    boolean remove(Long id) throws DatabaseException;
}
