package com.upsaude.service.support.logsauditoria;

import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.mapper.LogsAuditoriaMapper;
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
