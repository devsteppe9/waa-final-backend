package edu.miu.waa.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import edu.miu.waa.WaaApplication;
import edu.miu.waa.model.Property;
import edu.miu.waa.repo.PropertyRepo;
import edu.miu.waa.service.PropertyService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

  private final PropertyRepo propertyRepo;
  
  @Override
  @Transactional(readOnly = true)
  public List<Property> findAllProperties() {
    return propertyRepo.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Property> findPropertyById(long id) {
    return propertyRepo.findById(id);
  }

  @Override
  public Property create(Property property) {
    propertyRepo.save(property);
    return property;
  }

  @Override
  public void updateById(long id, Property property) {
    
    Property persistedProperty =
        propertyRepo
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Cannot find property: %d".formatted(id)));
    BeanUtils.copyProperties(property, persistedProperty);  
  }

  @Override
  public void delete(Property property) {
    propertyRepo.delete(property);
  }

  @Override
  public Property patch(long id, String jsonPatch) throws JsonProcessingException {
    Property property = propertyRepo.findById(id).orElseThrow(() ->
        new ResourceAccessException("Cannot find property: %d".formatted(id)));
    JsonNode jsonNode = WaaApplication.objectMapper.readTree(jsonPatch);
    WaaApplication.objectMapper.updateValue(property, jsonNode);
    propertyRepo.save(property);
    return property;
  }
}
