package com.cs5324.monitorbackend.exception;

public class MediaDoesNotExistException extends RuntimeException{
    public MediaDoesNotExistException(){
        super("Media does not exist");
    }
}
