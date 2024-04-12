package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfEvent;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus approvalStatus = ItemStatus.PENDING;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Notification notification;

    @OneToOne(mappedBy = "event")
    private Page page;

    @ManyToOne
    private User user;
}
