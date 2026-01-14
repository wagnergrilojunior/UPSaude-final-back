package com.upsaude.service.api.anexo;

import com.upsaude.enums.TargetTypeAnexoEnum;

import java.util.UUID;

public interface AnexoTargetPermissionValidator {
    void validarAcesso(TargetTypeAnexoEnum targetType, UUID targetId, UUID tenantId);
}
