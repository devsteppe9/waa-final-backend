package edu.miu.waa.service;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface LocalStorageService {
  void init();

  void store(MultipartFile file, String storageKey);

  Stream<Path> loadAll();

  Path load(String filename);

  void copyFileResourceContent(String key, OutputStream outputStream);

  Resource loadAsResource(String filename);

  void deleteAll();

}
