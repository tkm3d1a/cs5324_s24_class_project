package com.cs5324.monitorbackend.repository;

import com.cs5324.monitorbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> getRoleByName(String roleName);
}
