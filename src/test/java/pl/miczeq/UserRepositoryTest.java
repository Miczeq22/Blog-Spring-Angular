package pl.miczeq;

import org.junit.Before;
import org.junit.Test;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.repository.impl.UserRepositoryImpl;

public class UserRepositoryTest {
    private UserRepository userRepository;

    @Before
    public void init() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void saveTest() {
        try {
            userRepository.save(new User("user", "user123", "User", "User", "user@email.com"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            userRepository.update(1L, new User("admin", "admin123", "ADMIN", "Admin", "admin@email.com"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneTest() {
        try {
            System.out.println(userRepository.findOne(1L));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneByUsernameTest() {
        try {
            System.out.println(userRepository.findOne("user"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() {
        try {
            userRepository.findAll().forEach(user -> System.out.println(user));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
