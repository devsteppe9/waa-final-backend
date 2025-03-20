package edu.miu.waa.controller;

import edu.miu.waa.WaaApplication;
import edu.miu.waa.config.TestSecurityConfig;
import edu.miu.waa.model.Role;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.RoleRepo;
import edu.miu.waa.service.UserService;
import java.util.Optional;
import org.apiguardian.api.API;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = WaaApplication.class, properties = "spring.profiles.active=unit-test")
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-unit-test.properties")
@Transactional
public class AbstractControllerTest {
  @Autowired
  protected RoleRepo roleRepo;
  
  @Autowired
  protected UserService userService;
  
  protected Role getRole(String role) {
    Optional<Role> persisted = roleRepo.findRoleByRole(role);
    if (persisted.isPresent()) {
      return persisted.get();
    }
    Role newRole = new Role();
    newRole.setRole(role);
    return roleRepo.save(newRole);
  }
  
  protected User createUser(String name, String role) {
    User user = new User();
    user.setUsername(name);
    user.setPassword("password");
    user.setEmail(name);
    user.getRoles().add(getRole(role));
    userService.addUser(user);
    return user;
  }
  }
