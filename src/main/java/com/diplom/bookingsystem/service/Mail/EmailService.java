package com.diplom.bookingsystem.service.Mail;

public interface EmailService{
    void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment);
}
