package edu.miu.waa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.miu.waa.model.Property;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
  List<Property> findAllProperties();
  Optional<Property> findPropertyById(long id);
  void create(Property property);
  void updateById(long id, Property property);
  void delete(Property property);
  Property patch(long id, String jsonPatch) throws JsonProcessingException;
}
