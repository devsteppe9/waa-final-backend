package edu.miu.waa.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.miu.waa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends AbstractServiceTest {
  
  @Autowired
  private UserService userService;
  
  @Test
  void testUpdateUserStatus() {
    User user = new User();
    user.setUsername("user1");
    user.setFirstName("User");
    user.setLastName("One");
    user.setEmail("user1@waa.com");
    user.setPassword("password");
    user.setEnabled(false);

    User newUser  = userService.addUser(user);

    userService.updateUserStatus(newUser.getId(), true);

    assertTrue(userService.findByUsername(newUser.getUsername()).getEnabled());
  }
}
