package edu.miu.waa.service.impl;

import edu.miu.waa.model.Property;
import edu.miu.waa.repo.PropertyRepo;
import edu.miu.waa.service.PropertyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public void create(Property property) {
    propertyRepo.save(property);
  }
}
