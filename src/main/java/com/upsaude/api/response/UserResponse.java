package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String email;
    private String role;
    private String phone;
    private OffsetDateTime emailConfirmedAt;
    private OffsetDateTime lastSignInAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
