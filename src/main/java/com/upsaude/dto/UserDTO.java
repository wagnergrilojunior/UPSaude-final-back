package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO para User.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String email;
    private String role;
    private String phone;
    private OffsetDateTime emailConfirmedAt;
    private OffsetDateTime lastSignInAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

