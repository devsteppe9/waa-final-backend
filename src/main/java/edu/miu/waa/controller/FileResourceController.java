package edu.miu.waa.controller;

import edu.miu.waa.model.FileResource;
import edu.miu.waa.service.FileResourceService;
import edu.miu.waa.service.LocalStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/file-resources")
@RequiredArgsConstructor
public class FileResourceController {
  
  private final FileResourceService fileResourceService;
  private final LocalStorageService localStorageService;

  @GetMapping("/{storageKey}")
  public ResponseEntity<Resource> getImage(@PathVariable String storageKey, HttpServletResponse response) {
    FileResource fileResource = fileResourceService.getByStorageKey(storageKey);
    
    response.setContentType(fileResource.getContentType());

    response.setHeader(
        HttpHeaders.CONTENT_LENGTH,
        String.valueOf(fileResourceService.getFileResourceContentLength(fileResource)));
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileResource.getName());

    Resource file = localStorageService.loadAsResource(
        fileResource.getStorageKey());
    if ( file == null ) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}
