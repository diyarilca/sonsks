package com.springback.sksbackend.service.Impl;

import com.springback.sksbackend.entity.User;
import com.springback.sksbackend.service.EmailService;
import com.springback.sksbackend.validation.exception.ActivationNotificationException;
import com.springback.sksbackend.validation.exception.NotUniqueEmailException;
import com.springback.sksbackend.repository.UserRepository;
import com.springback.sksbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    EmailService emailService;

    @Transactional(rollbackOn = MailException.class)
    @Override
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        } catch (DataIntegrityViolationException exception){
            throw new NotUniqueEmailException();
        } catch (MailException exception){
            throw new ActivationNotificationException();
        }
    }
}


