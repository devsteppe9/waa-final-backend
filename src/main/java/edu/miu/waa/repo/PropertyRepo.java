package edu.miu.waa.repo;


import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepo extends JpaRepository<Property, Long> {

  Optional<Object> findByName(String name);
  
  @Query("SELECT p FROM Property p WHERE p.expirationDate is null OR p.expirationDate > CURRENT_TIMESTAMP ORDER BY p.created DESC")
  List<Property> findAllPropertiesSortByCreated_Desc();

  @Query("SELECT p FROM Property p WHERE p.owner.id = :userId ORDER BY p.created DESC ")
  List<Property> findAllByUserPropertiesSortByCreated_Desc(@Param("userId") long userId);
}
