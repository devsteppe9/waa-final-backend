package edu.miu.waa.repo;

import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserRepoTest extends AbstractRepoTest {
    @Autowired
    private UserRepo userRepo;
    // Add your test cases here
    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("user2");
        user.setFirstName("User");
        user.setLastName("One");
        user.setEmail("user2@waa.com");
        user.setPassword("password");
        user.setEnabled(false);
        userRepo.save(user);

        assertTrue(userRepo.findById(user.getId()).isPresent());
    }
    @Test
    void testUpdateUserStatus() {
        User user = new User();
        user.setUsername("user1");
        user.setFirstName("User");
        user.setLastName("One");
        user.setEmail("user1@waa.com");
        user.setPassword("password");
        user.setEnabled(false);

        User newUser  = userRepo.save(user);

        userRepo.updateUserStatus(newUser.getId(), true);

        assertTrue(userRepo.findById(newUser.getId()).orElse(null).getEnabled());
    }
}
