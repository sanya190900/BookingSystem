package com.diplom.bookingsystem.service.Mail.Impl;

import com.diplom.bookingsystem.exceptions.MessageException;
import com.diplom.bookingsystem.service.Mail.EmailService;
import com.diplom.bookingsystem.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @Override
    public void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            String text = userService.addLinkToEmail(body, pathToAttachment).orElseThrow();
            mimeMessageHelper.setText("", text);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MessageException("Sending email error: " + e.getMessage());
        }
    }
}
