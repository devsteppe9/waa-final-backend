package edu.miu.waa.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import edu.miu.waa.WaaApplication;
import edu.miu.waa.dto.request.PropertyRequestDto;
import edu.miu.waa.dto.response.PropertyResponseDto;
import edu.miu.waa.model.*;
import edu.miu.waa.repo.OfferRepo;
import edu.miu.waa.repo.PropertyRepo;
import edu.miu.waa.security.service.CurrentUserService;
import edu.miu.waa.service.FavouriteService;
import edu.miu.waa.service.PropertyService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

  private final PropertyRepo propertyRepo;
  private final OfferRepo offerRepo;
  private final ModelMapper modelMapper;
  private final CurrentUserService currentUserService;
  private final FavouriteService favouriteService;

  @Override
  @Transactional(readOnly = true)
  public List<Property> findAllProperties() {
    return propertyRepo.findAllPropertiesSortByCreated_Desc();
  }

  @Override
  public void update(Property property) {
    propertyRepo.save(property);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PropertyResponseDto> findAllPropertiesWithFavs() {

    User user = currentUserService.getCurrentUser();
    List<Property> properties = propertyRepo.findAllPropertiesSortByCreated_Desc();
    properties.forEach(
        property -> {
          property.setFavourites(
              property.getFavourites().stream()
                  .filter(fav -> fav.getUser().getId() == user.getId())
                  .collect(Collectors.toList()));
        });

    return properties.stream()
        .map(property -> modelMapper.map(property, PropertyResponseDto.class))
        .collect(Collectors.toList());
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
    property.setStatus(
        property.getStatus() != null ? property.getStatus() : PropertyStatus.AVAILABLE);
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
    Property property =
        propertyRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceAccessException("Cannot find property: %d".formatted(id)));
    JsonNode jsonNode = WaaApplication.objectMapper.readTree(jsonPatch);
    WaaApplication.objectMapper.updateValue(property, jsonNode);

    // when property sold, set all offers to rejected
    if (property.getStatus() != null && property.getStatus() == PropertyStatus.SOLD) {
      property.setExpirationDate(LocalDateTime.now().plusMinutes(5));
      property.getOffers().forEach(offer -> {
        offer.setStatus(OfferStatusEnum.REJECTED);
        offerRepo.save(offer);
      });
    }

    propertyRepo.save(property);

    return property;
  }
}
