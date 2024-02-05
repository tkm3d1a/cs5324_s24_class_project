package com.cs5324.monitorbackend.responsebody;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String message;
    private String errorMessage;
}
