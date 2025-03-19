package edu.miu.waa.service.impl;

import com.google.common.hash.HashCode;
import edu.miu.waa.model.FileResource;
import edu.miu.waa.service.FileCloudService;
import edu.miu.waa.service.JCloudStore;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jclouds.blobstore.domain.Blob;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileCloudServiceImpl implements FileCloudService {

  private final JCloudStore jCloudStore;
  
  @Override
  public InputStream getFileResourceContent(String key) {
    return null;
  }

  @Override
  public long getFileResourceContentLength(String key) {
    return 0;
  }

  @Async
  @Override
  public String saveFileResourceContent(FileResource fileResource, byte[] bytes) {
    Blob blob = createBlob(fileResource, bytes);
    jCloudStore.putBlob(blob);
    return fileResource.getStorageKey();
  }

  @Override
  public String saveFileResourceContent(FileResource fileResource, File file) {
    return "";
  }

  @Override
  public void deleteFileResourceContent(String key) {

  }

  @Override
  public boolean fileResourceContentExists(String key) {
    return false;
  }

  @Override
  public URI getSignedGetContentUri(String key) {
    return null;
  }

  @Override
  public void copyContent(String key, OutputStream output)
      throws IOException, NoSuchElementException {

  }

  @Override
  public byte[] copyContent(String key) throws IOException, NoSuchElementException {
    return new byte[0];
  }

  private Blob createBlob(@Nonnull FileResource fileResource, @Nonnull byte[] bytes) {
    return jCloudStore
        .getBlobStore()
        .blobBuilder(fileResource.getStorageKey())
        .payload(bytes)
        .contentLength(bytes.length)
        .contentMD5(   HashCode.fromString(fileResource.getContentMd5()))
        .contentType(fileResource.getContentType())
        .contentDisposition("filename=" + fileResource.getName())
        .build();
  }
}
