package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @NotBlank
    @Size(min = 5, message = "Title must be at least 5 characters long")
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(min = 5, message = "Content must be at least 5 characters long")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private ItemStatus status = ItemStatus.PENDING;

    @NotNull
    @Column(nullable = false)
    private Boolean isTagged = false; //Changed to match same flag from Page

    // 0 to 1 relationship with media
    // inverse relationship is 0 to 1
    @OneToOne
    private Media media;

    @OneToOne(cascade = CascadeType.ALL)
    private Notification notification;
}
