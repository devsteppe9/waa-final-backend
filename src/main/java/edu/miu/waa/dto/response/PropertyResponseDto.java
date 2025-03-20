package edu.miu.waa.dto.response;

import edu.miu.waa.model.PropertyStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
  private Date created;
  private Date expirationDate;
  private PropertyStatus status;
  private UserOutDto user;
}
