package edu.miu.waa.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.miu.waa.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertyRepoTest {
  
  @Autowired
  private PropertyRepo propertyRepo;
  
  @Test
  void testAddProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);
    
    assertTrue(propertyRepo.findAll().stream()
        .anyMatch(p -> p.getName().equals("test")));
  }
  
  @Test
  void testDeleteProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);
    propertyRepo.delete(property);
    
    assertTrue(propertyRepo.findAll().stream()
        .noneMatch(p -> p.getName().equals("test")));
  }
  
  @Test
  void testUpdateProperty() {
    Property property = new Property();
    property.setName("test");
    propertyRepo.save(property);
    
    property.setName("test2");
    propertyRepo.save(property);
    assertTrue(propertyRepo.findAll().stream()
        .anyMatch(p -> p.getName().equals("test2")));
  }
}
