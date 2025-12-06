package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest;
import com.upsaude.api.response.embeddable.InformacoesAtendimentoResponse;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.dto.embeddable.InformacoesAtendimentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InformacoesAtendimentoMapper {
    InformacoesAtendimento toEntity(InformacoesAtendimentoRequest request);
    InformacoesAtendimentoResponse toResponse(InformacoesAtendimento entity);
    void updateFromRequest(InformacoesAtendimentoRequest request, @MappingTarget InformacoesAtendimento entity);

    // Mapeamento entre DTO e Entity
    InformacoesAtendimento toEntity(com.upsaude.dto.embeddable.InformacoesAtendimentoDTO dto);
    com.upsaude.dto.embeddable.InformacoesAtendimentoDTO toDTO(InformacoesAtendimento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.InformacoesAtendimentoDTO dto, @MappingTarget InformacoesAtendimento entity);
}
