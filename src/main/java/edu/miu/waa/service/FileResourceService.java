package edu.miu.waa.service;

import edu.miu.waa.model.FileResource;
import edu.miu.waa.model.Property;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;
import org.springframework.web.multipart.MultipartFile;

public interface FileResourceService {
  void saveFileResource(Property property, MultipartFile[] file) throws IOException;
  byte[] getFileResource(String fileName);
  void deleteFileResource(String fileName);
  long getFileResourceContentLength(FileResource fileResource);
  void copyFileResourceContent(FileResource fileResource, OutputStream outputStream)
      throws IOException;
  FileResource getById(long id) throws NoSuchElementException;

  FileResource getByStorageKey(String storageKey) throws NoSuchElementException;
}
