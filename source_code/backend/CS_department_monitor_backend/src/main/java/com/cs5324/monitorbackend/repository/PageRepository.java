package com.cs5324.monitorbackend.repository;

import com.cs5324.monitorbackend.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID>{

}
