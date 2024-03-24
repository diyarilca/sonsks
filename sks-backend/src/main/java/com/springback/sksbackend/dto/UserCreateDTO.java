package com.springback.sksbackend.dto;

import com.springback.sksbackend.entity.User;
import com.springback.sksbackend.validation.error.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(


    @NotBlank(message = "{hoaxify.constraint.username.notblank}")
    @Size(min = 4,max = 25)
    String userName,


    @NotBlank
    @Email
    @UniqueEmail
    String email,


    @Size(min = 8,max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{hoaxify.constraint.password.pattern}")
    String password
)
{
    public User toUser(){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
