package edu.miu.waa.dto.response;

import edu.miu.waa.model.Property;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDto {
  private Long id;
  private String name;
  private String description;
  private List<FileResourceDto> fileResources = new ArrayList<>();
  
  public PropertyDto(Property property) {
    this.id = property.getId();
    this.name = property.getName();
    this.description = property.getDescription();
    this.fileResources = property.getFileResources().stream().map(FileResourceDto::new).collect(
        Collectors.toList());
  }
}
