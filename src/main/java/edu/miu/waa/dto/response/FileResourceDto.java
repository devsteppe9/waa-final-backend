package edu.miu.waa.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.miu.waa.model.FileResource;
import edu.miu.waa.model.Property;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResourceDto extends RepresentationModel<FileResourceDto> {
  private long id;
  private String name;
  private long contentLength;
  private String contentType;
  private String fileName;
  private String storageKey;
  private String contentMd5;
  private long propertyId;
  
  public FileResourceDto(FileResource fileResource) {
    this.id = fileResource.getId();
    this.name = fileResource.getName();
    this.contentLength = fileResource.getContentLength();
    this.contentType = fileResource.getContentType();
    this.fileName = fileResource.getFileName();
    this.storageKey = fileResource.getStorageKey();
    this.contentMd5 = fileResource.getContentMd5();
    this.propertyId = fileResource.getProperty().getId();
  }
}
