package edu.miu.waa.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.miu.waa.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PropertyRepoTest extends AbstractRepoTest {

  @Autowired
  private PropertyRepo propertyRepo;

  @Test
  void testAddProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);
    
    assertTrue(propertyRepo.findById(property.getId()).isPresent());
  }

   @Test
  void testDeleteProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);
    propertyRepo.delete(property);
    
    assertTrue(propertyRepo.findById(property.getId()).isEmpty());
  }

  @Test
  void testUpdateProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);

    property.setName("test2");
    propertyRepo.save(property);
    assertTrue(propertyRepo.findByName("test2").isPresent());
  }
}
