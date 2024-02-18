package com.cs5324.monitorbackend.commandlinerunner;

import com.cs5324.monitorbackend.entity.Role;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.repository.RoleRepository;
import com.cs5324.monitorbackend.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Order(value = 1)
public class DefaultAdminRunner implements CommandLineRunner {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    private static final String USERNAME = "test_admin";
    private static final String PASSWORD = "password";
    private static final String ROLE_NAME = "ADMIN";

    @Override
    public void run(String... args) throws Exception {
        log.info("----------------------------------------------");
        log.info("------- START RUNNER: ADMIN CREATE  ----------");
        log.info("----------------------------------------------");
        Optional<Role> adminRoleOpt = roleRepository.getRoleByName(ROLE_NAME);
        Role adminRole;
        if(adminRoleOpt.isEmpty()){
            log.info("Role not present in DB...");
            adminRole = new Role();
            adminRole.setName(ROLE_NAME);
            Role savedRole = roleRepository.save(adminRole);
            log.info("Added Role: {}", savedRole);
        } else {
            log.info("Role present in DB...");
            adminRole = adminRoleOpt.get();
            log.info("Existing Role: {}", adminRole);
        }

        User adminUser = userRepository.findByUsername(USERNAME);
        if(adminUser == null){
            log.info("Default user not present in DB...");
            adminUser = new User();
            adminUser.setEnabled(true);
            adminUser.setUsername(USERNAME);
            adminUser.setPassword(passwordEncoder.encode(PASSWORD));
            adminUser.addRoleToUser(adminRole);
            User savedAdminUser = userRepository.save(adminUser);
            log.info("Added User: {}", savedAdminUser);
        } else {
            log.info("User present in DB...");
            log.info("Existing User: {}",adminUser);
        }
        log.info("----------------------------------------------");
        log.info("-------- END RUNNER: ADMIN CREATE  -----------");
        log.info("----------------------------------------------");
    }
}
