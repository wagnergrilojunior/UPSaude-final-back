package com.upsaude.mapper.sistema.auditoria;

import com.upsaude.api.request.sistema.auditoria.LogsAuditoriaRequest;
import com.upsaude.api.response.sistema.auditoria.LogsAuditoriaResponse;
import com.upsaude.entity.sistema.auditoria.LogsAuditoria;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface LogsAuditoriaMapper {

    @Mapping(target = "active", ignore = true)
    LogsAuditoria toEntity(LogsAuditoriaResponse dto);

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
