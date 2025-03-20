package edu.miu.waa.dto.response;

import edu.miu.waa.model.Offer;
import edu.miu.waa.model.Property;
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
  private List<OfferResponseDto> offers = new ArrayList<>();
  private List<FileResourceDto> fileResources = new ArrayList<>();
  
  public PropertyResponseDto(Property property) {
    this.id = property.getId();
    this.name = property.getName();
    this.description = property.getDescription();
    this.fileResources =  property.getFileResources() != null ? property.getFileResources().stream().map(FileResourceDto::new).collect(
        Collectors.toList()) : new ArrayList<>();
  }
}
