package pl.miczeq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miczeq.exception.BadUserException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(User user) {
        boolean result;
        try {
            if (user.getId() != null) {
                result = userRepository.update(user.getId(), user);
            } else {
                result = userRepository.save(user);
            }
        } catch (BadUserException | DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public User findOne(Long id) {
        User user;

        try {
            user = userRepository.findOne(id);
        } catch (DatabaseException e) {
            user = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findOneByUsername(String username) {
        User user;

        try {
            user = userRepository.findOneByUsername(username);
        } catch (DatabaseException e) {
            user = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findOneByEmail(String email) {
        User user;

        try {
            user = userRepository.findOneByEmail(email);
        } catch (DatabaseException e) {
            user = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users;

        try {
            users = userRepository.findAll();
        } catch (DatabaseException e) {
            users = null;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean remove(Long id) {
        boolean result;

        try {
            result = userRepository.remove(id);
        } catch (DatabaseException e) {
            result = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
