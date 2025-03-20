package edu.miu.waa.controller;


import static edu.miu.waa.util.HttpServletRequestPaths.generateFileResourceLink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.miu.waa.dto.request.PropertyRequestDto;
import edu.miu.waa.dto.response.FileResourceDto;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.dto.response.PropertyResponseDto;
import edu.miu.waa.model.Property;
import edu.miu.waa.service.FileResourceService;
import edu.miu.waa.service.LocalStorageService;
import edu.miu.waa.service.OfferService;
import edu.miu.waa.service.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

  private final ModelMapper modelMapper;
  private final PropertyService propertyService;
  private final FileResourceService fileResourceService;
  private final LocalStorageService localStorageService;
  private final OfferService offerService;
  private final Long currentUserId = 1L; // TODO: get current user id
  
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PropertyResponseDto> getAllProperties() {
    return propertyService.findAllProperties().stream().map(property -> modelMapper.map(property, PropertyResponseDto.class)).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable long id, HttpServletRequest request) {
    Optional<Property> property = propertyService.findPropertyById(id);
    if (!property.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    PropertyResponseDto p = modelMapper.map(property.get(), PropertyResponseDto.class);
    p.getFileResources().forEach(fileResource -> {
      fileResource.setHref(generateFileResourceLink(fileResource.getStorageKey(), request));
    });
    return ResponseEntity.ok(p);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public PropertyResponseDto create(@RequestBody PropertyRequestDto propertyDto) {
    Property property = propertyService.create(propertyDto);
    return modelMapper.map(property, PropertyResponseDto.class);
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
  public ResponseEntity deletePropertyById(@PathVariable long id) {
    Optional<Property> property = propertyService.findPropertyById(id);
    if (!property.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    propertyService.delete(property.get());
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
  @ResponseStatus(HttpStatus.CREATED)
  public PropertyResponseDto uploadImage(@PathVariable long id, @RequestParam MultipartFile[] files, HttpServletRequest request)
      throws IOException {
    Property property = propertyService.findPropertyById(id)
        .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    fileResourceService.saveFileResource(property, files);

    PropertyResponseDto response = modelMapper.map(property, PropertyResponseDto.class);

    response.getFileResources().forEach(fileResource -> {
      fileResource.setHref(generateFileResourceLink(fileResource.getStorageKey(), request));
    });
    
    return response;
  }

  @GetMapping("/{id}/images")
  public List<FileResourceDto> getImages(@PathVariable long id, HttpServletRequest request) {
    Property property = propertyService.findPropertyById(id)
        .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    return property.getFileResources().stream()
        .map((element) -> {
          FileResourceDto dto = modelMapper.map(element, FileResourceDto.class);
        dto.setHref(generateFileResourceLink(element.getStorageKey(), request));
        return dto;
    }).collect(Collectors.toList());
  }

  @GetMapping("/{propertyId}/offers")
  @ResponseStatus(HttpStatus.OK)
  public List<OfferResponseDto> getOffersByPropertyId(@PathVariable long propertyId) {
    return offerService.findAllOffersByPropertyId(currentUserId, propertyId);
  }
  
}
