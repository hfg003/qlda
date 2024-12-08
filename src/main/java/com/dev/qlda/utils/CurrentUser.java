package com.dev.qlda.utils;

import com.dev.qlda.entity.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    public static Users get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof Users selectedUser) {
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
}