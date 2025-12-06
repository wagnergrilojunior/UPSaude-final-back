package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AnamneseConsultaRequest;
import com.upsaude.api.response.embeddable.AnamneseConsultaResponse;
import com.upsaude.entity.embeddable.AnamneseConsulta;
import com.upsaude.dto.embeddable.AnamneseConsultaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AnamneseConsultaMapper {
    AnamneseConsulta toEntity(AnamneseConsultaRequest request);
    AnamneseConsultaResponse toResponse(AnamneseConsulta entity);
    void updateFromRequest(AnamneseConsultaRequest request, @MappingTarget AnamneseConsulta entity);

    // Mapeamento entre DTO e Entity
    AnamneseConsulta toEntity(com.upsaude.dto.embeddable.AnamneseConsultaDTO dto);
    com.upsaude.dto.embeddable.AnamneseConsultaDTO toDTO(AnamneseConsulta entity);
    void updateFromDTO(com.upsaude.dto.embeddable.AnamneseConsultaDTO dto, @MappingTarget AnamneseConsulta entity);
}
