package com.upsaude.entity.sistema;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users", schema = "auth")
@Data
public class User {

    @Column(name = "instance_id")
    private UUID instanceId;

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "aud", length = 255)
    private String aud;

    @Column(name = "role", length = 255)
    private String role;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "encrypted_password", length = 255)
    private String encryptedPassword;

    @Column(name = "email_confirmed_at")
    private OffsetDateTime emailConfirmedAt;

    @Column(name = "invited_at")
    private OffsetDateTime invitedAt;

    @Column(name = "confirmation_token", length = 255)
    private String confirmationToken;

    @Column(name = "confirmation_sent_at")
    private OffsetDateTime confirmationSentAt;

    @Column(name = "recovery_token", length = 255)
    private String recoveryToken;

    @Column(name = "recovery_sent_at")
    private OffsetDateTime recoverySentAt;

    @Column(name = "email_change_token_new", length = 255)
    private String emailChangeTokenNew;

    @Column(name = "email_change", length = 255)
    private String emailChange;

    @Column(name = "email_change_sent_at")
    private OffsetDateTime emailChangeSentAt;

    @Column(name = "last_sign_in_at")
    private OffsetDateTime lastSignInAt;

    @Column(name = "raw_app_meta_data", columnDefinition = "jsonb")
    private String rawAppMetaData;

    @Column(name = "raw_user_meta_data", columnDefinition = "jsonb")
    private String rawUserMetaData;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone_confirmed_at")
    private OffsetDateTime phoneConfirmedAt;

    @Column(name = "phone_change")
    private String phoneChange;

    @Column(name = "phone_change_token", length = 255)
    private String phoneChangeToken;

    @Column(name = "phone_change_sent_at")
    private OffsetDateTime phoneChangeSentAt;

    @Column(name = "confirmed_at")
    private OffsetDateTime confirmedAt;

    @Column(name = "email_change_token_current", length = 255)
    private String emailChangeTokenCurrent;

    @Column(name = "email_change_confirm_status")
    private Short emailChangeConfirmStatus;

    @Column(name = "banned_until")
    private OffsetDateTime bannedUntil;

    @Column(name = "reauthentication_token", length = 255)
    private String reauthenticationToken;

    @Column(name = "reauthentication_sent_at")
    private OffsetDateTime reauthenticationSentAt;

    @Column(name = "is_sso_user", nullable = false)
    private Boolean isSsoUser;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous;
}
