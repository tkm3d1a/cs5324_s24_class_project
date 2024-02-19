package com.cs5324.monitorbackend.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super(MessageFormat.format("User {0} not found",username));
    }
}
