package com.upsaude.service.api.support.logsauditoria;

import com.upsaude.api.response.sistema.auditoria.LogsAuditoriaResponse;
import com.upsaude.entity.sistema.auditoria.LogsAuditoria;
import com.upsaude.mapper.sistema.auditoria.LogsAuditoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsAuditoriaResponseBuilder {

    private final LogsAuditoriaMapper mapper;

    public LogsAuditoriaResponse build(LogsAuditoria entity) {
        return mapper.toResponse(entity);
    }
}
