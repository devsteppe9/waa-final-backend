package edu.miu.waa.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import edu.miu.waa.WaaApplication;
import edu.miu.waa.dto.request.PropertyRequestDto;
import edu.miu.waa.dto.response.PropertyResponseDto;
import edu.miu.waa.model.Favourite;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.PropertyStatus;
import edu.miu.waa.model.User;
import edu.miu.waa.repo.PropertyRepo;
import edu.miu.waa.security.service.CurrentUserService;
import edu.miu.waa.service.FavouriteService;
import edu.miu.waa.service.PropertyService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

  private final PropertyRepo propertyRepo;
  private final ModelMapper modelMapper;
  private final CurrentUserService currentUserService;
  private final FavouriteService favouriteService;
  
  @Override
  @Transactional(readOnly = true)
  public List<Property> findAllProperties() {
    return propertyRepo.findAllPropertiesSortByCreated_Desc();
  }

  @Override
  @Transactional(readOnly = true)
  public List<PropertyResponseDto> findAllPropertiesWithFavs() {
    User user = currentUserService.getCurrentUser();
    List<Property> properties = propertyRepo.findAllPropertiesSortByCreated_Desc();
    List<PropertyResponseDto> dtos = properties.stream()
        .map(property -> modelMapper.map(property, PropertyResponseDto.class))
        .collect(Collectors.toList());
    
    List<Favourite> favs = favouriteService.findByUserAndProperties(user, properties);

    dtos.forEach(
        property -> {
          Optional<Favourite> hasFav =
              favs.stream().filter(fav -> fav.getId() == property.getId()).findFirst();
          if (hasFav.isPresent()) {
            property.setFavouriteId(hasFav.get().getId());
          }
        });
    return dtos;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Property> findPropertyById(long id) {
    return propertyRepo.findById(id);
  }

  @Override
  public Property create(PropertyRequestDto propertyDto) {
    User currentUser = currentUserService.getCurrentUser();
    Property property = modelMapper.map(propertyDto, Property.class);
    property.setOwner(currentUser);
    property.setCreated(LocalDateTime.now());
    property.setStatus(property.getStatus() != null ? property.getStatus() : PropertyStatus.AVAILABLE);
    propertyRepo.save(property);
    return property;
  }

  @Override
  public Property create(Property property) {
    return propertyRepo.save(property);
  }

  @Override
  public void updateById(long id, PropertyRequestDto property) {
    
    Property persistedProperty =
        propertyRepo
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Cannot find property: %d".formatted(id)));
    BeanUtils.copyProperties(property, persistedProperty);  
  }

  @Override
  public void delete(Property property) {
    if (property.getOffers() != null && property.getOffers().size() > 0) {
      throw new IllegalArgumentException("Cannot delete property with offers");
    }
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
