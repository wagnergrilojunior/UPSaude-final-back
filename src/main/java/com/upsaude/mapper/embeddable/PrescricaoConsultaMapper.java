package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.PrescricaoConsultaRequest;
import com.upsaude.api.response.embeddable.PrescricaoConsultaResponse;
import com.upsaude.entity.embeddable.PrescricaoConsulta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PrescricaoConsultaMapper {
    PrescricaoConsulta toEntity(PrescricaoConsultaRequest request);
    PrescricaoConsultaResponse toResponse(PrescricaoConsulta entity);
    void updateFromRequest(PrescricaoConsultaRequest request, @MappingTarget PrescricaoConsulta entity);

}
