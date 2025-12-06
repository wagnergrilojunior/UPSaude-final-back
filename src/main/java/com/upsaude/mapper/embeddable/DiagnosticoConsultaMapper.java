package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoConsultaRequest;
import com.upsaude.api.response.embeddable.DiagnosticoConsultaResponse;
import com.upsaude.entity.embeddable.DiagnosticoConsulta;
import com.upsaude.dto.embeddable.DiagnosticoConsultaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DiagnosticoConsultaMapper {
    DiagnosticoConsulta toEntity(DiagnosticoConsultaRequest request);
    DiagnosticoConsultaResponse toResponse(DiagnosticoConsulta entity);
    void updateFromRequest(DiagnosticoConsultaRequest request, @MappingTarget DiagnosticoConsulta entity);

    // Mapeamento entre DTO e Entity
    DiagnosticoConsulta toEntity(com.upsaude.dto.embeddable.DiagnosticoConsultaDTO dto);
    com.upsaude.dto.embeddable.DiagnosticoConsultaDTO toDTO(DiagnosticoConsulta entity);
    void updateFromDTO(com.upsaude.dto.embeddable.DiagnosticoConsultaDTO dto, @MappingTarget DiagnosticoConsulta entity);
}
