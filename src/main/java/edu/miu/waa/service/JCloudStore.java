package edu.miu.waa.service;

import static org.jclouds.Constants.PROPERTY_ENDPOINT;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.Properties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.jclouds.s3.reference.S3Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JCloudStore {

  private static final String JCLOUDS_PROVIDER_KEY_FILESYSTEM = "filesystem";
  private static final String JCLOUDS_PROVIDER_KEY_AWS_S3 = "aws-s3";
  private static final String JCLOUDS_PROVIDER_KEY_S3 =
      "s3"; // for s3 compatible API providers like Minio
  private static final String JCLOUDS_PROVIDER_KEY_TRANSIENT = "transient";
  private static final List<String> SUPPORTED_PROVIDERS =
      List.of(
          JCLOUDS_PROVIDER_KEY_FILESYSTEM,
          JCLOUDS_PROVIDER_KEY_AWS_S3,
          JCLOUDS_PROVIDER_KEY_S3,
          JCLOUDS_PROVIDER_KEY_TRANSIENT);

  private FileStoreConfig fileStoreConfig;
  private BlobStoreContext blobStoreContext;

  @Value("${cloud.store.externalDirectoryPath:#{null}}")
  private String externalDirectoryPath;
  
  @Value("${cloud.store.provider}")
  private String provider;
  
  @Value("${cloud.store.location}")
  private String location;

  @Value("${cloud.store.endpoint}")
  private String endpoint;
  
  @Value("${cloud.store.container}")
  private String container;
  
  @Value("${cloud.store.identity}")
  private String identity;
  
  @Value("${cloud.store.secret}")
  private String secret;

  JCloudStore() {
  }

  private String validateProvider(String provider) {
    if (!SUPPORTED_PROVIDERS.contains(provider)) {
      throw new IllegalArgumentException(
          "Configuration contains unsupported file store provider '"
          + provider
          + "'. Falling back to file system provider instead.");
    }

    if (JCLOUDS_PROVIDER_KEY_FILESYSTEM.equals(provider)
        && externalDirectoryPath == null) {
      throw new IllegalArgumentException(
          "File system file store provider could not be configured; external directory is not set. ");
    }

    return provider;
  }

  private Properties configureOverrides(String provider, String endpoint) {
    if (JCLOUDS_PROVIDER_KEY_FILESYSTEM.equals(provider)) {
      Properties overrides = new Properties();
      overrides.setProperty(
          "jclouds.filesystem.basedir", externalDirectoryPath);
      return overrides;
    }

    if (JCLOUDS_PROVIDER_KEY_AWS_S3.equals(provider)) {
      Properties overrides = new Properties();
      overrides.setProperty(S3Constants.PROPERTY_S3_VIRTUAL_HOST_BUCKETS, "false");
      return overrides;
    }

    if (JCLOUDS_PROVIDER_KEY_S3.equals(provider)) {
      Properties overrides = new Properties();
      overrides.setProperty(S3Constants.PROPERTY_S3_VIRTUAL_HOST_BUCKETS, "false");

      if (StringUtils.isNotEmpty(endpoint)) {
        overrides.setProperty(PROPERTY_ENDPOINT, endpoint);
      }
      return overrides;
    }

    return new Properties();
  }

  @PostConstruct
  public void init() {
    validateProvider(provider);
    fileStoreConfig = new FileStoreConfig(provider, location, container);
    blobStoreContext =
        ContextBuilder.newBuilder(provider)
            .credentials(identity, secret)
            .overrides(configureOverrides(provider, endpoint))
            .build(BlobStoreContext.class);
    Location cloudLocation = createLocation(fileStoreConfig.provider, fileStoreConfig.location);
    blobStoreContext.getBlobStore().createContainerInLocation(cloudLocation, fileStoreConfig.container);

    log.info(
        "File store configured with provider: '{}', container: '{}' and location: '{}'.",
        fileStoreConfig.provider,
        fileStoreConfig.container,
        fileStoreConfig.location);
  }

  @PreDestroy
  public void cleanUp() {
    blobStoreContext.close();
  }

  private static Location createLocation(String provider, String location) {
    if (location == null) {
      // some BlobStores allow specifying a location, such as US-EAST, where containers will exist.
      // null will choose a default location.
      return null;
    }

    Location parent =
        new LocationBuilder()
            .scope(LocationScope.PROVIDER)
            .id(provider)
            .description(provider)
            .build();

    return new LocationBuilder()
        .scope(LocationScope.REGION)
        .id(location)
        .description(location)
        .parent(parent)
        .build();
  }

  public boolean blobExists(String key) {
    return key != null && getBlobStore().blobExists(getBlobContainer(), key);
  }

  public Blob getBlob(String key) {
    return getBlobStore().getBlob(getBlobContainer(), key);
  }

  public PageSet<? extends StorageMetadata> getBlobList(ListContainerOptions options) {
    return getBlobStore().list(getBlobContainer(), options);
  }
  
  public void putBlob(Blob blob) {
    getBlobStore().putBlob(getBlobContainer(), blob);
  }

  public void removeBlob(String key) {
    getBlobStore().removeBlob(getBlobContainer(), key);
  }

  public void deleteDirectory(String dirName) {
    getBlobStore().deleteDirectory(getBlobContainer(), dirName);
  }

  public String getBlobContainer() {
    return fileStoreConfig.container;
  }

  public BlobStore getBlobStore() {
    return blobStoreContext.getBlobStore();
  }

  public BlobRequestSigner getBlobRequestSigner() {
    return blobStoreContext.getSigner();
  }

  public boolean isUsingFileSystem() {
    return JCLOUDS_PROVIDER_KEY_FILESYSTEM.equals(fileStoreConfig.getProvider());
  }

  @Data
  private static class FileStoreConfig {
    private final String provider;
    private final String location;
    private final String container;
  }
}
