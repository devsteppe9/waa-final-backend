package edu.miu.waa.service;

import edu.miu.waa.dto.UserInDto;
import edu.miu.waa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest extends AbstractServiceTest {
  
  @Autowired
  private UserService userService;

  @Test
  void testAddUser() {
    UserInDto user = new UserInDto();
    user.setUsername("user1");
    user.setFirstName("User");
    user.setLastName("One");
    user.setEmail("user1@waa.com");
    user.setPassword("password");
    user.setEnabled(false);
    user.setRole("OWNER");

    User newUser = userService.addUser(user);

    assertNotNull(newUser);
    assertEquals("user1", newUser.getUsername());
    assertEquals("User", newUser.getFirstName());
    assertEquals("One", newUser.getLastName());
    assertEquals("user1@waa.com", newUser.getEmail());
    assertEquals(false, newUser.getEnabled());
    assertEquals("OWNER", newUser.getRoles().get(0).getRole());
  }

  @Test
  void testUpdateUserStatus() {
    UserInDto user = new UserInDto();
    user.setUsername("user1");
    user.setFirstName("User");
    user.setLastName("One");
    user.setEmail("user1@waa.com");
    user.setPassword("password");
    user.setEnabled(false);
    user.setRole("CUSTOMER");

    User newUser  = userService.addUser(user);

    userService.updateUserStatus(newUser.getId(), true);

    assertTrue(userService.findByUsername(newUser.getUsername()).getEnabled());
  }


}
