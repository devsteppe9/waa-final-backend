package edu.miu.waa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyRequestDto {
  private String name;
  private String description;
  private Double price;
}
