package edu.miu.waa.repo;

import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepo extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

}
