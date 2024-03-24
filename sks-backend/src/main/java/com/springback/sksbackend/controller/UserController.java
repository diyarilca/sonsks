package com.springback.sksbackend.controller;

import com.springback.sksbackend.dto.UserCreateDTO;
import com.springback.sksbackend.validation.error.Messages;
import com.springback.sksbackend.validation.error.ApiError;
import com.springback.sksbackend.validation.error.GenericMessage;
import com.springback.sksbackend.validation.exception.ActivationNotificationException;
import com.springback.sksbackend.validation.exception.NotUniqueEmailException;
import com.springback.sksbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping("/api/users")
    GenericMessage createUser(@Valid @RequestBody UserCreateDTO user) {

        userService.save(user.toUser());

        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message",LocaleContextHolder.getLocale());

        return new GenericMessage(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> NotValidExcpetion(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/users");
        String message = Messages.getMessageForLocale("hoaxify.error.validation",LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);

        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap
                (FieldError::getField,FieldError::getDefaultMessage, (existing,replacing) -> existing));

        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    ResponseEntity<ApiError> NotUniqueEmailException(NotUniqueEmailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        apiError.setValidationErrors(exception.getValidationErrors());

        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    ResponseEntity<ApiError> ActivationNotificationException(NotUniqueEmailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(502);

        return ResponseEntity.status(502).body(apiError);
    }
}
