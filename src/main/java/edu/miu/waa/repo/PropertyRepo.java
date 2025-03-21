package edu.miu.waa.repo;


import edu.miu.waa.model.Property;
import java.util.List;
import java.util.Optional;

import edu.miu.waa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropertyRepo extends JpaRepository<Property, Long> {

  Optional<Object> findByName(String name);
  
  @Query("SELECT p FROM Property p ORDER BY p.created DESC")
  List<Property> findAllPropertiesSortByCreated_Desc();

  @Query("SELECT p FROM Property p WHERE p.owner = ?1 ORDER BY p.created DESC")
  List<Property> findAllPropertiesByUserSortByCreated_Desc(User user);
}
