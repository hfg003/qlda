package com.dev.qlda.security;

import com.dev.qlda.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSessionDetail {
    private final UserRepo userRepo;

    public UserDetailsService get(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // TODO Auto-generated method stub
                return (UserDetails) userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
            }
        };
    }
}
