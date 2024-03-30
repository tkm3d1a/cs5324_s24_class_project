package com.cs5324.monitorbackend.exception;

import java.text.MessageFormat;
import java.util.UUID;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(UUID postId) {
        super(MessageFormat.format("Post {0} not found",postId.toString()));
    }

    public PostNotFoundException(String message, UUID userId) {
        super(message + ":" + userId.toString());
    }
}
