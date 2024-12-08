package com.dev.qlda.repo;

import com.dev.qlda.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles, String> {
}
