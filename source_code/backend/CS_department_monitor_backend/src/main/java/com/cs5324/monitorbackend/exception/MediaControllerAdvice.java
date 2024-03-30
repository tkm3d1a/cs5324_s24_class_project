package com.cs5324.monitorbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class MediaControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidMediaTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(InvalidMediaTypeException exception){
        Map<String,String> errorMap = new LinkedHashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        return errorMap;
    }
}
