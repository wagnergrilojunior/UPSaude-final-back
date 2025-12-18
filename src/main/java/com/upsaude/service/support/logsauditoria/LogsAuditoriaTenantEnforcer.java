package com.upsaude.service.support.logsauditoria;

import com.upsaude.entity.sistema.LogsAuditoria;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.LogsAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogsAuditoriaTenantEnforcer {

    private final LogsAuditoriaRepository repository;

    public LogsAuditoria validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao log de auditoria. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Log de auditoria não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Log de auditoria não encontrado com ID: " + id);
            });
    }

    public LogsAuditoria validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
