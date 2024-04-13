package com.cs5324.monitorbackend.exception;

public class InvalidMediaTypeException extends RuntimeException{
    public InvalidMediaTypeException() {
        super("Invalid media type passed - media not saved");
    }
}
