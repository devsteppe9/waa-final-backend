package edu.miu.waa.service.impl;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import edu.miu.waa.model.FileResource;
import edu.miu.waa.model.Property;
import edu.miu.waa.repo.FileResourceRepo;
import edu.miu.waa.service.FileResourceService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.input.NullInputStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileResourceServiceImpl implements FileResourceService {
  
  private final FileResourceRepo fileResourceRepo;
  private final LocalStorageServiceImpl localStorageService;
  
  @Override
  public void saveFileResource(Property property, MultipartFile[] files)
      throws IOException {
    Arrays.stream(files).forEach(file -> {
      FileResource fileResource = new FileResource();
      fileResource.setFileName(file.getOriginalFilename());
      fileResource.setContentType(file.getContentType());
      fileResource.setContentLength(file.getSize());
      ByteSource bytes = new MultipartFileByteSource(file);
      String contentMd5 = null;
      try {
        contentMd5 = bytes.hash(Hashing.md5()).toString();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      fileResource.setContentMd5(contentMd5);
      fileResource.setProperty(property);
      String storageKey = UUID.randomUUID().toString();
      fileResource.setStorageKey(storageKey);
      fileResourceRepo.save(fileResource);
      property.getFileResources().add(fileResource);

      // save file content
      localStorageService.store(file, storageKey);
    });
    
  }

  @Override
  public byte[] getFileResource(String fileName) {
    return new byte[0];
  }

  @Override
  public void deleteFileResource(String fileName) {

  }

  @Override
  public long getFileResourceContentLength(FileResource fileResource) {
    return fileResource.getContentLength();
  }

  @Override
  public void copyFileResourceContent(FileResource fileResource, OutputStream outputStream)
      throws NoSuchElementException, IOException {
  }

  @Override
  public FileResource getById(long id) throws NoSuchElementException {
    return fileResourceRepo.findById(id).orElseThrow(() -> new NoSuchElementException("FileResource not found"));
  }

  @Override
  public FileResource getByStorageKey(String storageKey) throws NoSuchElementException {
    return fileResourceRepo.findByStorageKey(storageKey).orElse(null);
  }

  private static class MultipartFileByteSource extends ByteSource {
    private MultipartFile file;

    public MultipartFileByteSource(MultipartFile file) {
      this.file = file;
    }

    @Override
    public InputStream openStream() {
      try {
        return file.getInputStream();
      } catch (IOException ioe) {
        return new NullInputStream(0);
      }
    }
  }
}
