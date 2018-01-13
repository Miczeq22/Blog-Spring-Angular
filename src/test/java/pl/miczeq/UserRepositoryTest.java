package pl.miczeq;

import org.junit.Before;
import org.junit.Test;
import pl.miczeq.exception.BadUserException;
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
            userRepository.save(new User("userxx", "", "User", "User", "userxx@email.com"));
        } catch (BadUserException | DatabaseException e) {
          e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        try {
            System.out.println(userRepository.update(1L, new User("admin", "admin123", "ADMIN", "Admin", "admin@email.com")));
        } catch (BadUserException | DatabaseException e) {
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
            System.out.println(userRepository.findOneByUsername("user"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOneByEmailTest() {
        try {
            System.out.println(userRepository.findOneByEmail("usser@email.com"));
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

    @Test
    public void removeTest() {
        try {
            userRepository.remove(3L);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
