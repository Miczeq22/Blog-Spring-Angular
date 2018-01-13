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
            userRepository.save(new User("admin", "admin123", "Admin", "Admin", "admin@admin.com"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
