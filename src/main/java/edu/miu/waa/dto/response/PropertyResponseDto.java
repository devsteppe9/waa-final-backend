package edu.miu.waa.dto.response;

import edu.miu.waa.model.Offer;
import edu.miu.waa.model.Property;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponseDto {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private String address;
  private String state;
  private Integer zipcode;
  private String city;
  private String country;
  private int totalBathrooms;
  private int totalBedrooms;
  private int totalArea;
  private List<OfferResponseDto> offers = new ArrayList<>();
  private List<FileResourceDto> fileResources = new ArrayList<>();
  private UserOutDto owner;
  public PropertyResponseDto(Property property) {
    this.id = property.getId();
    this.name = property.getName();
    this.description = property.getDescription();
    this.fileResources =  property.getFileResources() != null ? property.getFileResources().stream().map(FileResourceDto::new).collect(
        Collectors.toList()) : new ArrayList<>();
  }
}
