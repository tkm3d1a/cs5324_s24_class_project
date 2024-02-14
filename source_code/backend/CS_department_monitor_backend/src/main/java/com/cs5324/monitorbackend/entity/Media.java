package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.MediaType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String title;

    private MediaType mediaType;

    //  0 to 1 relationship with Post entity.
    // inverse relationship is 0 to 1
    @OneToOne(mappedBy = "media")
    private Post post;

    @OneToOne
    private Notification notification;
}
