package edu.miu.waa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResource {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private String name;
  private long contentLength;
  private String contentType;
  private String fileName;
  private String storageKey;
  private String contentMd5;
  
  @ManyToOne
  @JoinColumn(name = "property_id")
  @JsonBackReference
  private Property property;
}
