package com.upsaude.service.support.logsauditoria;

import com.upsaude.api.request.sistema.LogsAuditoriaRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class LogsAuditoriaValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do log de auditoria é obrigatório");
        }
    }

    public void validarObrigatorios(LogsAuditoriaRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do log de auditoria são obrigatórios");
        }
    }
}
