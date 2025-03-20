package edu.miu.waa.dto.request;

import java.util.Date;
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
  private String address;
  private String state;
  private Integer zipcode;
  private String city;
  private String country;
  private int totalBathrooms;
  private int totalBedrooms;
  private int totalArea;
  private Date expirationDate;
  private String status;
}
