package com.upsaude.service.support.logsauditoria;

import com.upsaude.api.request.sistema.LogsAuditoriaRequest;
import com.upsaude.entity.sistema.LogsAuditoria;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.LogsAuditoriaMapper;
import com.upsaude.repository.sistema.LogsAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogsAuditoriaCreator {

    private final LogsAuditoriaRepository repository;
    private final LogsAuditoriaMapper mapper;
    private final LogsAuditoriaValidationService validationService;
    private final LogsAuditoriaRelacionamentosHandler relacionamentosHandler;
    private final LogsAuditoriaDomainService domainService;

    public LogsAuditoria criar(LogsAuditoriaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        LogsAuditoria entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        LogsAuditoria saved = repository.save(Objects.requireNonNull(entity));
        log.info("Log de auditoria criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
