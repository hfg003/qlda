package com.dev.qlda.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserRequest {
    private String fullName;
    private String username;
    private String password;
    private String role;
    private String department;
}
