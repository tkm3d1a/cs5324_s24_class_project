package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/display", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
