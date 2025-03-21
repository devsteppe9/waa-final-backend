package edu.miu.waa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.miu.waa.dto.request.PropertyRequestDto;
import edu.miu.waa.dto.response.PropertyResponseDto;
import edu.miu.waa.model.Property;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface PropertyService {
  List<Property> findAllProperties();

  List<PropertyResponseDto> findAllPropertiesWithFavs();

  Optional<Property> findPropertyById(long id);
  Property create(PropertyRequestDto property);
  Property create(Property property);
  void updateById(long id, PropertyRequestDto property);
  void delete(Property property);
  Property patch(long id, String jsonPatch) throws JsonProcessingException;
}
