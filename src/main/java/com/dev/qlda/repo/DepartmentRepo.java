package com.dev.qlda.repo;

import com.dev.qlda.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Departments, String> {
}
