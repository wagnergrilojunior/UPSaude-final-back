package com.upsaude.mapper;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.dto.LogsAuditoriaDTO;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface LogsAuditoriaMapper extends EntityMapper<LogsAuditoria, LogsAuditoriaDTO> {

    @Mapping(target = "active", ignore = true)
    LogsAuditoria toEntity(LogsAuditoriaDTO dto);

    LogsAuditoriaDTO toDTO(LogsAuditoria entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    LogsAuditoria fromRequest(LogsAuditoriaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(LogsAuditoriaRequest request, @MappingTarget LogsAuditoria entity);

    LogsAuditoriaResponse toResponse(LogsAuditoria entity);
}
