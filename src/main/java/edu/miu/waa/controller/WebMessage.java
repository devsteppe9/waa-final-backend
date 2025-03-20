package edu.miu.waa.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebMessage {
  protected String message;
  protected HttpStatus status = HttpStatus.OK;
  public WebMessage(HttpStatus httpStatus) {
    this.status = httpStatus;
  }
  
  public WebMessage setMessage(String message) {
    this.message = message;
    return this;
  }
  
  public static WebMessage createWebMessage(String message, HttpStatus httpStatus) {
    return new WebMessage(httpStatus).setMessage(message);
  }
  
  public static WebMessage notFound(String message) {
    return createWebMessage(message, HttpStatus.NOT_FOUND);
  }
}
