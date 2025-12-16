package com.upsaude.service.support.logsauditoria;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.entity.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogsAuditoriaRelacionamentosHandler {

    public void resolver(LogsAuditoria entity, LogsAuditoriaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
        // sem relacionamentos adicionais
    }
}
