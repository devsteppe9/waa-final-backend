package edu.miu.waa.service;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String text);
}
