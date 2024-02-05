package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
}
