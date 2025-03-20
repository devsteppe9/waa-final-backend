package edu.miu.waa.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Property {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String description;
  
  private Double price;
  
  private String address;
  
  private String state;
  
  private Integer zipcode;
  
  private String city;
  
  private String country;
  
  private Integer totalBathrooms;
  
  private Integer totalBedrooms;
  
  private Integer totalArea;

  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<FileResource> fileResources = new ArrayList<>();

  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<Offer> offers = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;
}
