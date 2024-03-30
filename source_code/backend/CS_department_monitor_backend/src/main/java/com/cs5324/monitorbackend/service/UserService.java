package com.cs5324.monitorbackend.service;

import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public Optional<User> getUser(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
