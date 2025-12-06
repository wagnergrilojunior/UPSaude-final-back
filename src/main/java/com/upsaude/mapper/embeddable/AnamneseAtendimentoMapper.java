package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.response.embeddable.AnamneseAtendimentoResponse;
import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.dto.embeddable.AnamneseAtendimentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AnamneseAtendimentoMapper {
    AnamneseAtendimento toEntity(AnamneseAtendimentoRequest request);
    AnamneseAtendimentoResponse toResponse(AnamneseAtendimento entity);
    void updateFromRequest(AnamneseAtendimentoRequest request, @MappingTarget AnamneseAtendimento entity);

    // Mapeamento entre DTO e Entity
    AnamneseAtendimento toEntity(com.upsaude.dto.embeddable.AnamneseAtendimentoDTO dto);
    com.upsaude.dto.embeddable.AnamneseAtendimentoDTO toDTO(AnamneseAtendimento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.AnamneseAtendimentoDTO dto, @MappingTarget AnamneseAtendimento entity);
}
