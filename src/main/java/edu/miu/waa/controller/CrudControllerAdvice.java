package edu.miu.waa.controller;

import static edu.miu.waa.controller.WebMessage.createWebMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CrudControllerAdvice {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public WebMessage illegalArgumentException(IllegalArgumentException ex) {
    return createWebMessage(ex.getMessage(),  HttpStatus.CONFLICT);
  }
}
