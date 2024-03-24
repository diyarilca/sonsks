package com.springback.sksbackend.validation.error;

import com.springback.sksbackend.entity.User;
import com.springback.sksbackend.repository.UserRepository;
import com.springback.sksbackend.validation.error.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User inDataBase = userRepository.findByEmail(value);
        return inDataBase == null;
    }
}
