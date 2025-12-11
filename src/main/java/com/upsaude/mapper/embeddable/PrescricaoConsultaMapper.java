package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.PrescricaoConsultaRequest;
import com.upsaude.api.response.embeddable.PrescricaoConsultaResponse;
import com.upsaude.entity.embeddable.PrescricaoConsulta;
import com.upsaude.dto.embeddable.PrescricaoConsultaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PrescricaoConsultaMapper {
    PrescricaoConsulta toEntity(PrescricaoConsultaRequest request);
    PrescricaoConsultaResponse toResponse(PrescricaoConsulta entity);
    void updateFromRequest(PrescricaoConsultaRequest request, @MappingTarget PrescricaoConsulta entity);

    PrescricaoConsulta toEntity(com.upsaude.dto.embeddable.PrescricaoConsultaDTO dto);
    com.upsaude.dto.embeddable.PrescricaoConsultaDTO toDTO(PrescricaoConsulta entity);
    void updateFromDTO(com.upsaude.dto.embeddable.PrescricaoConsultaDTO dto, @MappingTarget PrescricaoConsulta entity);
}
