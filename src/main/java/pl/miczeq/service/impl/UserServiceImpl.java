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
    public boolean save(User user) throws BadUserException, DatabaseException {
        boolean result;

        if (user.getId() != null) {
            result = userRepository.update(user.getId(), user);
        } else {
            result = userRepository.save(user);
        }

        return result;
    }

    @Override
    public User findOne(Long id) throws DatabaseException {
        return userRepository.findOne(id);
    }

    @Override
    public User findOneByUsername(String username) throws DatabaseException {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public User findOneByEmail(String email) throws DatabaseException {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return userRepository.findAll();
    }

    @Override
    public boolean remove(Long id) throws DatabaseException {
        return userRepository.remove(id);
    }
}
