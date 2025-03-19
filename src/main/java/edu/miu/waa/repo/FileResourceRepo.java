package edu.miu.waa.repo;

import edu.miu.waa.model.FileResource;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileResourceRepo extends JpaRepository<FileResource, Long> {
  Optional<FileResource> findByStorageKey(String storageKey);
}
