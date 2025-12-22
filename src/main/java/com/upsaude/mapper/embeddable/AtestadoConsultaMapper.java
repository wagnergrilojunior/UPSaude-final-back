package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AtestadoConsultaRequest;
import com.upsaude.api.response.embeddable.AtestadoConsultaResponse;
import com.upsaude.entity.embeddable.AtestadoConsulta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AtestadoConsultaMapper {
    AtestadoConsulta toEntity(AtestadoConsultaRequest request);
    AtestadoConsultaResponse toResponse(AtestadoConsulta entity);
    void updateFromRequest(AtestadoConsultaRequest request, @MappingTarget AtestadoConsulta entity);

}
