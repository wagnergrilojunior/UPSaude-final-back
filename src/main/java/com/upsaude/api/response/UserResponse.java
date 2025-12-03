package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID instanceId;
    private String aud;
    private String role;
    private String email;
    private String encryptedPassword;
    private OffsetDateTime emailConfirmedAt;
    private OffsetDateTime invitedAt;
    private String confirmationToken;
    private OffsetDateTime confirmationSentAt;
    private String recoveryToken;
    private OffsetDateTime recoverySentAt;
    private String emailChangeTokenNew;
    private String emailChange;
    private OffsetDateTime emailChangeSentAt;
    private OffsetDateTime lastSignInAt;
    private String rawAppMetaData;
    private String rawUserMetaData;
    private Boolean isSuperAdmin;
    private String phone;
    private OffsetDateTime phoneConfirmedAt;
    private String phoneChange;
    private String phoneChangeToken;
    private OffsetDateTime phoneChangeSentAt;
    private OffsetDateTime confirmedAt;
    private String emailChangeTokenCurrent;
    private Short emailChangeConfirmStatus;
    private OffsetDateTime bannedUntil;
    private String reauthenticationToken;
    private OffsetDateTime reauthenticationSentAt;
    private Boolean isSsoUser;
    private OffsetDateTime deletedAt;
    private Boolean isAnonymous;
}
