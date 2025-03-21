package edu.miu.waa.data;

import edu.miu.waa.repo.RoleRepo;
import edu.miu.waa.repo.UserRepo;
import edu.miu.waa.model.Role;
import edu.miu.waa.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findUserByUsername("guest") == null) {
            User user = new User();
            user.setUsername("guest");
            user.setPassword(passwordEncoder.encode("Guest@123"));
            userRepository.save(user);
        }

        if (userRepository.findUserByUsername("admin") == null) {
            Role adminRole = roleRepository.findRoleByRole("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRoles(Collections.singletonList(adminRole));

            userRepository.save(admin);
        }
        if (userRepository.findUserByUsername("owner") == null) {
            Role clientRole = roleRepository.findRoleByRole("OWNER")
                    .orElseGet(() -> roleRepository.save(new Role("OWNER")));
            User user = new User();
            user.setUsername("owner");
            user.setPassword(passwordEncoder.encode("Owner@123"));
            user.setRoles(Collections.singletonList(clientRole));

            userRepository.save(user);
        }

        if (userRepository.findUserByUsername("customer") == null) {
            Role clientRole = roleRepository.findRoleByRole("CUSTOMER")
                    .orElseGet(() -> roleRepository.save(new Role("CUSTOMER")));
            User user = new User();
            user.setUsername("customer");
            user.setPassword(passwordEncoder.encode("Customer@123"));
            user.setRoles(Collections.singletonList(clientRole));

            userRepository.save(user);
        }
    }
}