package com.dev.qlda.controller;

import com.dev.qlda.entity.ContactAndSupport;
import com.dev.qlda.repo.ContactSupportRepo;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3010")
@RequiredArgsConstructor
public class ContactSupportController {
    private final ContactSupportRepo contactSupportRepo;

    @PostMapping("/create")
    public WrapResponse<?> createContactSupport(@RequestBody ContactAndSupport contactSupport) {
        ContactAndSupport contactAndSupport = MappingUtils.mapObject(contactSupport, ContactAndSupport.class);
        contactAndSupport.setId(UUID.randomUUID().toString());
        return WrapResponse.ok("Lien he thanh cong", contactSupportRepo.save(contactAndSupport));
    }

    @GetMapping("/{id}")
    public WrapResponse<?> getContactSupport(@PathVariable String id) {
        return WrapResponse.ok(contactSupportRepo.findById(id).orElse(null));
    }
}
