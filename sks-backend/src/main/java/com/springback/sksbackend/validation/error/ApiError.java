package com.springback.sksbackend.validation.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiError {

    private int status;

    private String message;

    private String path;

    private long timestamp = new Date().getTime();

    private Map<String,String> validationErrors = null;

}
