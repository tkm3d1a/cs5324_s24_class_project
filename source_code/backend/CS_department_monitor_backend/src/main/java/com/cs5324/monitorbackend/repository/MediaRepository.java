package com.cs5324.monitorbackend.repository;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID>{

    List<Media> findByItemStatusIn(Collection<@NotNull ItemStatus> itemStatus);
    List<Media> findByIsTaggedOrderByCreatedAtDesc(@NotNull Boolean isTagged);
}
