package com.cs5324.monitorbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.csrf().disable(); //May be old item - marked as deprecated
        httpSecurity.headers().frameOptions().disable(); //May be old item - marked as deprecated
        httpSecurity
                .authorizeHttpRequests() //May be old item - marked as deprecated
                .requestMatchers("/test").hasAuthority("ADMIN")
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated();
        return httpSecurity.build();
    }

}
