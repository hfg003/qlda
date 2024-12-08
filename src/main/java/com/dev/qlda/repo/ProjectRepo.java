package com.dev.qlda.repo;

import com.dev.qlda.entity.Projects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepo extends JpaRepository<Projects, String> {

    @Query("select p from Projects p")
    Page<Projects> getProjectsByPage(Pageable pageable);
}
