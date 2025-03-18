package edu.miu.waa.controller;

import edu.miu.waa.model.Property;
import edu.miu.waa.service.PropertyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
  
  private final PropertyService propertyService;
  
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Property> getAllProperties() {
    return propertyService.findAllProperties();
  }
}
