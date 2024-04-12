package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.entity.enums.MediaType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version private Long version;
    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;
    private String title;
    private String link;
    private MediaType mediaType;

    @NotNull
    @Column(nullable = false)
    private Boolean isTagged = false; //Added to match the tagging from Posts and Pages

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus = ItemStatus.PENDING;

    //  0 to 1 relationship with Post entity.
    // inverse relationship is 0 to 1
    @OneToOne
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "title"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Notification notification;

    @ManyToOne
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "username"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
}
