package com.upsaude.service.api.support.logsauditoria;

import com.upsaude.entity.sistema.auditoria.LogsAuditoria;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogsAuditoriaDomainService {

    public void aplicarDefaults(LogsAuditoria entity) {
        // sem regras adicionais por enquanto
    }

    public void validarPodeInativar(LogsAuditoria entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar log de auditoria já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Log de auditoria já está inativo");
        }
    }

    public void validarPodeDeletar(LogsAuditoria entity) {
        log.debug("Validando se log de auditoria pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}
