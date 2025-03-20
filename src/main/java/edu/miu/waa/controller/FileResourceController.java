package edu.miu.waa.controller;

import edu.miu.waa.dto.response.FileResourceDto;
import edu.miu.waa.model.FileResource;
import edu.miu.waa.service.FileResourceService;
import edu.miu.waa.service.LocalStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/file-resources")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
public class FileResourceController {
  
  private final FileResourceService fileResourceService;
  private final LocalStorageService localStorageService;
  
  @GetMapping("/{id}/")
  public ResponseEntity<FileResourceDto> getFileResource(@PathVariable long id) {
    FileResource fileResource = fileResourceService.getById(id);
    return ResponseEntity.ok(new FileResourceDto(fileResource));
  }

  @GetMapping(value = "/{storageKey}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public void getImage(@PathVariable String storageKey, HttpServletResponse response) {
    FileResource fileResource = fileResourceService.getByStorageKey(storageKey);
    
    if (fileResource == null) {
      throw new IllegalArgumentException("Cannot find: " + storageKey);
    }
    response.setContentType(fileResource.getContentType());
    response.setHeader(
        HttpHeaders.CONTENT_LENGTH,
        String.valueOf(fileResourceService.getFileResourceContentLength(fileResource)));
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileResource.getFileName());

    try {
      localStorageService.copyFileResourceContent(fileResource.getStorageKey(), response.getOutputStream());
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Could not find " + storageKey);
    }
  }
}
