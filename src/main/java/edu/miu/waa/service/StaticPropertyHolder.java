package edu.miu.waa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticPropertyHolder {
  public static String BACKEND_DOMAIN = "";

  @Value("${backend-domain:null}")
  public void setBackendDomain(String backendDomain) {
    this.BACKEND_DOMAIN = backendDomain;
  }
  public static String getBackendDomain() {
    return BACKEND_DOMAIN;
  }
}
