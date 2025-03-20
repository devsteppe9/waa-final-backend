package edu.miu.waa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
  
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime created;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime expirationDate;
  
  private PropertyStatus status;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User owner;
  
  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<FileResource> fileResources = new ArrayList<>();

  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<Offer> offers = new ArrayList<>();
  
  

}
