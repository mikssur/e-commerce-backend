package com.rest_api.fs14backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleResponse {
    private String name;
    private String email;
    private String role;
    private String picture;
}
