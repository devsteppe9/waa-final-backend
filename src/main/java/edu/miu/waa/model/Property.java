package edu.miu.waa.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @Column(length = 10000)
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

  @Basic
  private LocalDateTime created;

  @Basic
  private LocalDateTime expirationDate;
  
  @Enumerated(EnumType.ORDINAL)
  private PropertyStatus status;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User owner;
  
  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<FileResource> fileResources = new ArrayList<>();

  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<Offer> offers = new ArrayList<>();
  
  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
  private List<Favourite> favourites = new ArrayList<>();
}
