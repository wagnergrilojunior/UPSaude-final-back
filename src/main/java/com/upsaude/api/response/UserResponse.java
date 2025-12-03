package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO para User.
 *
 * @author UPSaúde
 */
@Data
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

