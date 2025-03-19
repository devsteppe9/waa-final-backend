package edu.miu.waa.service.exception;

public class StorageFileNotFoundException extends StorageException {
  
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
