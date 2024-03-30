package com.cs5324.monitorbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET).permitAll()
                            .requestMatchers(HttpMethod.POST).authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

//    @Bean
//    @Order(0)
//    SecurityFilterChain securityFilterChainBase(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity
//                .authorizeHttpRequests() //May be old item - marked as deprecated
//                .requestMatchers("/").permitAll();
//        return httpSecurity.build();
//    }

}
