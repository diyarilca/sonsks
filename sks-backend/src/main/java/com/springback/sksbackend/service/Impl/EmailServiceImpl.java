package com.springback.sksbackend.service.Impl;

import com.springback.sksbackend.config.HoaxifyProperties;
import com.springback.sksbackend.service.EmailService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    JavaMailSenderImpl mailSender;

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    @PostConstruct
    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoaxifyProperties.getEmail().host());
        mailSender.setPort(hoaxifyProperties.getEmail().port());
        mailSender.setUsername(hoaxifyProperties.getEmail().username());
        mailSender.setPassword(hoaxifyProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
    }

    public void sendActivationEmail(String email,String activationToken){
        var activationUrl = hoaxifyProperties.getClient().host() + "/activation" + activationToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@my-app.com");
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText(activationUrl);
        this.mailSender.send(message);
    }
}
