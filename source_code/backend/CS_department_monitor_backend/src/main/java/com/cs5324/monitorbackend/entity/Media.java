package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.entity.enums.MediaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
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

    @NotNull
    @Column(nullable = false)
    private Boolean isTagged = false; //Added to match the tagging from Posts and Pages

    @NotNull
    @Column(nullable = false)
    private ItemStatus itemStatus = ItemStatus.PENDING;

    //  0 to 1 relationship with Post entity.
    // inverse relationship is 0 to 1
    @OneToOne
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    private Notification notification;

    @ManyToOne
    private User user;
}
