package com.cs5324.monitorbackend.repository;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.User;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{
    List<Post> findPostByUser(User user);
    List<Post> findByStatusIn(Collection<@NotNull ItemStatus> itemStatus);
    List<Post> findByIsTaggedOrderByCreatedAtDesc(@NotNull Boolean isTagged);
}
