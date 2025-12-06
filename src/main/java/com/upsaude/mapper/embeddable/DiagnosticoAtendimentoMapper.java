package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
import com.upsaude.api.response.embeddable.DiagnosticoAtendimentoResponse;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.dto.embeddable.DiagnosticoAtendimentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DiagnosticoAtendimentoMapper {
    DiagnosticoAtendimento toEntity(DiagnosticoAtendimentoRequest request);
    DiagnosticoAtendimentoResponse toResponse(DiagnosticoAtendimento entity);
    void updateFromRequest(DiagnosticoAtendimentoRequest request, @MappingTarget DiagnosticoAtendimento entity);

    // Mapeamento entre DTO e Entity
    DiagnosticoAtendimento toEntity(com.upsaude.dto.embeddable.DiagnosticoAtendimentoDTO dto);
    com.upsaude.dto.embeddable.DiagnosticoAtendimentoDTO toDTO(DiagnosticoAtendimento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.DiagnosticoAtendimentoDTO dto, @MappingTarget DiagnosticoAtendimento entity);
}
