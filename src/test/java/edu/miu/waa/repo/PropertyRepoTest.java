package edu.miu.waa.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.miu.waa.model.Property;
import java.time.LocalDateTime;
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
  
  @Test
  void testGetSortByCreated() throws InterruptedException {
    Property property1 = new Property();
    property1.setName("test1");
    property1.setCreated(LocalDateTime.now().plusHours(1));
    propertyRepo.save(property1);
    
    Property property2 = new Property();
    property2.setName("test2");
    property2.setCreated(LocalDateTime.now().plusHours(3));
    propertyRepo.save(property2);
    assertTrue(propertyRepo.findAllPropertiesSortByCreated_Desc().size() > 0);
    assertEquals(property2, propertyRepo.findAllPropertiesSortByCreated_Desc().get(0));
    assertEquals(property1, propertyRepo.findAllPropertiesSortByCreated_Desc().get(1));
  }
 }
