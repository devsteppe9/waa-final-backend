package edu.miu.waa.controller;

import edu.miu.waa.dto.response.FavouriteResponseDto;
import edu.miu.waa.model.Favourite;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import edu.miu.waa.service.FavouriteService;
import edu.miu.waa.service.PropertyService;
import edu.miu.waa.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
public class FavouriteController {
  private final FavouriteService favouriteService;
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final PropertyService propertyService;

  @GetMapping("/user/{id}")
  public ResponseEntity<List<FavouriteResponseDto>> findByUser(@PathVariable long id) {
    User user = userService.findById(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(
        favouriteService.findByUser(user).stream()
            .map(f -> modelMapper.map(f, FavouriteResponseDto.class))
            .collect(Collectors.toList()));
  }

  @GetMapping("/property/{id}")
  public ResponseEntity<List<FavouriteResponseDto>> findByProperty(@PathVariable long id) {
    Optional<Property> property = propertyService.findPropertyById(id);
    if (!property.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(
        favouriteService.findByProperty(property.get()).stream()
            .map(f -> modelMapper.map(f, FavouriteResponseDto.class))
            .collect(Collectors.toList()));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable long id) {
    Optional<Favourite> fav = favouriteService.findById(id);
    if (!fav.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    favouriteService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
  
  @DeleteMapping("/user/{userId}/property/{propertyId}")
  public ResponseEntity deleteByUserAndPropertyId(@PathVariable long userId, @PathVariable long propertyId) {
    User user = userService.findById(userId);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    
    Optional<Property> propety = propertyService.findPropertyById(propertyId);
    if (!propety.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    
    favouriteService.deleteByUserAndPropertyId(user, propertyId);
    return ResponseEntity.noContent().build();
  }
}
