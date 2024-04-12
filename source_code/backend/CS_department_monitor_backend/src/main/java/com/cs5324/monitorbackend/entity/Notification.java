package com.cs5324.monitorbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Long version;

    // Post relationship
    @OneToOne(mappedBy = "notification", cascade = CascadeType.PERSIST)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "title"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Post post;

    // Media relationship
    @OneToOne(mappedBy = "notification", cascade = CascadeType.PERSIST)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "title"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Media media;

    // Event relationship
    @OneToOne(mappedBy = "notification", cascade = CascadeType.PERSIST)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "dateOfEvent"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Event event;

    @PrePersist
    @PreUpdate
    private void verifyRelationships() {
        int relationCount = ((post != null) ? 1 : 0) + ((media != null) ? 1 : 0) + ((event != null) ? 1 : 0);
        if (relationCount != 1)
            throw new PersistenceException("Invalid number of relationships detected. Expected 1, found " + relationCount);
    }
}
