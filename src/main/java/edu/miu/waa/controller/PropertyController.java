package edu.miu.waa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.miu.waa.model.FileResource;
import edu.miu.waa.model.Property;
import edu.miu.waa.service.FileResourceService;
import edu.miu.waa.service.LocalStorageService;
import edu.miu.waa.service.PropertyService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {
  
  private final PropertyService propertyService;
  private final FileResourceService fileResourceService;
  private final LocalStorageService localStorageService;
  
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
  
  @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
  public void uploadImage(@PathVariable long id, @RequestParam MultipartFile[] files)
      throws IOException {
    Property property = propertyService.findPropertyById(id)
        .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    fileResourceService.saveFileResource(property, files);
  }
  
  @GetMapping("/{id}/images")
  public List<FileResource> getImages(@PathVariable long id) {
    Property property = propertyService.findPropertyById(id)
        .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    
    return property.getFileResources();
  }
  
}
