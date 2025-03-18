package edu.miu.waa.repo;

import edu.miu.waa.model.Role;
import edu.miu.waa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRole(String role);
}
