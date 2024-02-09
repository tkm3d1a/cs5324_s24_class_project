package com.cs5324.monitorbackend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Long version;

    // Post relationship
    @OneToOne(mappedBy = "notification")
    private Post post;

    // Media relationship
    @OneToOne(mappedBy = "notification")
    private Media media;

    // Event relationship
    @OneToOne(mappedBy = "notification")
    private Event event;
}
