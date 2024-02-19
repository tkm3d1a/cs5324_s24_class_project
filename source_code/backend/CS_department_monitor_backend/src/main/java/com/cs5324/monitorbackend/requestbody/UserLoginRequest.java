package com.cs5324.monitorbackend.requestbody;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String hashedPassword;
}
