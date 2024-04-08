package com.cs5324.monitorbackend.exception;

public class EventDoesNotExistException extends RuntimeException{
    public EventDoesNotExistException(){
        super("Event does not exist");
    }
}
