package com.upsaude.service.support.logsauditoria;

import com.upsaude.api.request.sistema.LogsAuditoriaRequest;
import com.upsaude.entity.sistema.LogsAuditoria;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.sistema.LogsAuditoriaMapper;
import com.upsaude.repository.sistema.LogsAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogsAuditoriaUpdater {

    private final LogsAuditoriaRepository repository;
    private final LogsAuditoriaTenantEnforcer tenantEnforcer;
    private final LogsAuditoriaValidationService validationService;
    private final LogsAuditoriaRelacionamentosHandler relacionamentosHandler;
    private final LogsAuditoriaDomainService domainService;
    private final LogsAuditoriaMapper mapper;

    public LogsAuditoria atualizar(UUID id, LogsAuditoriaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        LogsAuditoria entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        LogsAuditoria saved = repository.save(Objects.requireNonNull(entity));
        log.info("Log de auditoria atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
