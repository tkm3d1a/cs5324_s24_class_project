package com.cs5324.monitorbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User /*implements UserDetails*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Length(min=8)
    private String password;
    private String username;

    @Email
    private String email;
    private boolean enabled;
    private boolean verified;

    //TODO: Needs authority/roles from Spring Security
}
