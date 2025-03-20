package edu.miu.waa.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailSenderController {
  
  @PostMapping
  public void sendEmail() {}
}
