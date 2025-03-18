package edu.miu.waa.service;

import edu.miu.waa.model.Property;
import java.util.List;

public interface PropertyService {
  List<Property> findAllProperties();
  void create(Property property);
}
