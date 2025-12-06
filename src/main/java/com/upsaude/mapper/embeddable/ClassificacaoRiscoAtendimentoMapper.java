package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoRiscoAtendimentoRequest;
import com.upsaude.api.response.embeddable.ClassificacaoRiscoAtendimentoResponse;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.dto.embeddable.ClassificacaoRiscoAtendimentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoRiscoAtendimentoMapper {
    ClassificacaoRiscoAtendimento toEntity(ClassificacaoRiscoAtendimentoRequest request);
    ClassificacaoRiscoAtendimentoResponse toResponse(ClassificacaoRiscoAtendimento entity);
    void updateFromRequest(ClassificacaoRiscoAtendimentoRequest request, @MappingTarget ClassificacaoRiscoAtendimento entity);

    // Mapeamento entre DTO e Entity
    ClassificacaoRiscoAtendimento toEntity(com.upsaude.dto.embeddable.ClassificacaoRiscoAtendimentoDTO dto);
    com.upsaude.dto.embeddable.ClassificacaoRiscoAtendimentoDTO toDTO(ClassificacaoRiscoAtendimento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ClassificacaoRiscoAtendimentoDTO dto, @MappingTarget ClassificacaoRiscoAtendimento entity);
}
