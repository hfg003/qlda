package com.dev.qlda.repo;

import com.dev.qlda.entity.ContactAndSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactSupportRepo extends JpaRepository<ContactAndSupport, String> {
    @Query("select c from ContactAndSupport c")
    Page<ContactAndSupport> getContactInPage(Pageable pageable);
}
