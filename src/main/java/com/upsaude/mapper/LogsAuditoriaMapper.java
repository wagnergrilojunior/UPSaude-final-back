package com.upsaude.mapper;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.dto.LogsAuditoriaDTO;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface LogsAuditoriaMapper extends EntityMapper<LogsAuditoria, LogsAuditoriaDTO> {

    @Mapping(target = "tenant", ignore = true)
    LogsAuditoria toEntity(LogsAuditoriaDTO dto);

    LogsAuditoriaDTO toDTO(LogsAuditoria entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    LogsAuditoria fromRequest(LogsAuditoriaRequest request);

    LogsAuditoriaResponse toResponse(LogsAuditoria entity);
}

