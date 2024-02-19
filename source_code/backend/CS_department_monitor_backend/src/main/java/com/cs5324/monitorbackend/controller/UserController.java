package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Resource
    private UserService userService;
}
