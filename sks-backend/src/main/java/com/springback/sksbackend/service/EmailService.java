package com.springback.sksbackend.service;

import com.springback.sksbackend.entity.User;

public interface EmailService {

    void sendActivationEmail(String email,String activationToken);

    void initialize();
}
