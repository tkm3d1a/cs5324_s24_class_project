package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("count", userService.getAllUsers().size());
        response.put("users", userService.getAllUsers());
        return ResponseEntity.ok().body(response);
    }
}
