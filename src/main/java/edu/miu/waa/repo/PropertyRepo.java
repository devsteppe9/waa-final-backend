package edu.miu.waa.repo;


import edu.miu.waa.model.Property;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepo extends JpaRepository<Property, Long> {

  Optional<Object> findByName(String name);
}
