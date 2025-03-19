package edu.miu.waa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.miu.waa.model.Property;
import edu.miu.waa.service.PropertyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PropertyController {
  
  private final PropertyService propertyService;
  
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Property> getAllProperties() {
    return propertyService.findAllProperties();
  }
  
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Property> getPropertyById(@PathVariable long id) {
    return propertyService.findPropertyById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
  
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Long create(Property property) {
    propertyService.create(property);
    return property.getId();
  }
  
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable long id, @RequestBody Property property) {
    propertyService.updateById(id, property);
  }
  
  @PatchMapping(value = "/{id}", consumes = "application/json")
  public ResponseEntity patch(@PathVariable long id, @RequestBody String jsonPatch) {
    try {
      propertyService.patch(id, jsonPatch);
    } catch (JsonMappingException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (JsonProcessingException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }
  
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePropertyById(@PathVariable long id) {
    Property property = propertyService.findPropertyById(id)
        .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    propertyService.delete(property);
  }
}
