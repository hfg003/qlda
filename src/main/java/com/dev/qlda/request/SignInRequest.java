package com.dev.qlda.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest {

    private String username;
    private String password;
}
