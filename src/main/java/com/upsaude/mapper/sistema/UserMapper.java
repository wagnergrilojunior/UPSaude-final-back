package com.upsaude.mapper.sistema;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.UserRequest;
import com.upsaude.api.response.sistema.UserResponse;
import com.upsaude.dto.sistema.UserDTO;
import com.upsaude.entity.sistema.User;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface UserMapper extends EntityMapper<User, UserDTO> {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instanceId", ignore = true)
    @Mapping(target = "aud", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "emailConfirmedAt", ignore = true)
    @Mapping(target = "invitedAt", ignore = true)
    @Mapping(target = "confirmationToken", ignore = true)
    @Mapping(target = "confirmationSentAt", ignore = true)
    @Mapping(target = "recoveryToken", ignore = true)
    @Mapping(target = "recoverySentAt", ignore = true)
    @Mapping(target = "emailChangeTokenNew", ignore = true)
    @Mapping(target = "emailChange", ignore = true)
    @Mapping(target = "emailChangeSentAt", ignore = true)
    @Mapping(target = "lastSignInAt", ignore = true)
    @Mapping(target = "rawAppMetaData", ignore = true)
    @Mapping(target = "rawUserMetaData", ignore = true)
    @Mapping(target = "isSuperAdmin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "phoneConfirmedAt", ignore = true)
    @Mapping(target = "phoneChange", ignore = true)
    @Mapping(target = "phoneChangeToken", ignore = true)
    @Mapping(target = "phoneChangeSentAt", ignore = true)
    @Mapping(target = "confirmedAt", ignore = true)
    @Mapping(target = "emailChangeTokenCurrent", ignore = true)
    @Mapping(target = "emailChangeConfirmStatus", ignore = true)
    @Mapping(target = "bannedUntil", ignore = true)
    @Mapping(target = "reauthenticationToken", ignore = true)
    @Mapping(target = "reauthenticationSentAt", ignore = true)
    @Mapping(target = "isSsoUser", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isAnonymous", ignore = true)
    User fromRequest(UserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instanceId", ignore = true)
    @Mapping(target = "aud", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "emailConfirmedAt", ignore = true)
    @Mapping(target = "invitedAt", ignore = true)
    @Mapping(target = "confirmationToken", ignore = true)
    @Mapping(target = "confirmationSentAt", ignore = true)
    @Mapping(target = "recoveryToken", ignore = true)
    @Mapping(target = "recoverySentAt", ignore = true)
    @Mapping(target = "emailChangeTokenNew", ignore = true)
    @Mapping(target = "emailChange", ignore = true)
    @Mapping(target = "emailChangeSentAt", ignore = true)
    @Mapping(target = "lastSignInAt", ignore = true)
    @Mapping(target = "rawAppMetaData", ignore = true)
    @Mapping(target = "rawUserMetaData", ignore = true)
    @Mapping(target = "isSuperAdmin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "phoneConfirmedAt", ignore = true)
    @Mapping(target = "phoneChange", ignore = true)
    @Mapping(target = "phoneChangeToken", ignore = true)
    @Mapping(target = "phoneChangeSentAt", ignore = true)
    @Mapping(target = "confirmedAt", ignore = true)
    @Mapping(target = "emailChangeTokenCurrent", ignore = true)
    @Mapping(target = "emailChangeConfirmStatus", ignore = true)
    @Mapping(target = "bannedUntil", ignore = true)
    @Mapping(target = "reauthenticationToken", ignore = true)
    @Mapping(target = "reauthenticationSentAt", ignore = true)
    @Mapping(target = "isSsoUser", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isAnonymous", ignore = true)
    void updateFromRequest(UserRequest request, @MappingTarget User entity);

    UserResponse toResponse(User entity);
}
