package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ExamesSolicitadosConsultaRequest;
import com.upsaude.api.response.embeddable.ExamesSolicitadosConsultaResponse;
import com.upsaude.entity.embeddable.ExamesSolicitadosConsulta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ExamesSolicitadosConsultaMapper {
    ExamesSolicitadosConsulta toEntity(ExamesSolicitadosConsultaRequest request);
    ExamesSolicitadosConsultaResponse toResponse(ExamesSolicitadosConsulta entity);
    void updateFromRequest(ExamesSolicitadosConsultaRequest request, @MappingTarget ExamesSolicitadosConsulta entity);

}
