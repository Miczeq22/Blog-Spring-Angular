package pl.miczeq.service;

import pl.miczeq.model.User;

import java.util.List;

public interface UserService {
    boolean save(User user);

    User findOne(Long id);

    User findOneByUsername(String username);

    User findOneByEmail(String email);

    List<User> findAll();

    boolean remove(Long id);
}
