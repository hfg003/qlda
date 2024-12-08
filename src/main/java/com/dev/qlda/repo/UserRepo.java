package com.dev.qlda.repo;

import com.dev.qlda.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);

    @Query("select u from Users u")
    Page<Users> getUsersInPage(Pageable pageable);
}
