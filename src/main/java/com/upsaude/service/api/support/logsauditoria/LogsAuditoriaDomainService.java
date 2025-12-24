package com.upsaude.service.api.support.logsauditoria;

import com.upsaude.entity.sistema.auditoria.LogsAuditoria;
import org.springframework.stereotype.Service;

@Service
public class LogsAuditoriaDomainService {

    public void aplicarDefaults(LogsAuditoria entity) {
        // sem regras adicionais por enquanto
    }
}
