package com.cs5324.monitorbackend.entity;

import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String content;

    @NotNull
    @Column(nullable = false)
    private Boolean isTagged = false;

    @NotNull
    @Column(nullable = false)
    private ItemStatus itemStatus = ItemStatus.PENDING;

    @NotNull
    @OneToOne
    private Event event;

    @ManyToOne
    private User user;
}
