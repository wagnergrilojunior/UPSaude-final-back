package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AnamneseConsultaRequest;
import com.upsaude.api.response.embeddable.AnamneseConsultaResponse;
import com.upsaude.entity.embeddable.AnamneseConsulta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AnamneseConsultaMapper {
    AnamneseConsulta toEntity(AnamneseConsultaRequest request);
    AnamneseConsultaResponse toResponse(AnamneseConsulta entity);
    void updateFromRequest(AnamneseConsultaRequest request, @MappingTarget AnamneseConsulta entity);

}
