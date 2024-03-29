package com.cs5324.monitorbackend.repository;

import com.cs5324.monitorbackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>{

}
