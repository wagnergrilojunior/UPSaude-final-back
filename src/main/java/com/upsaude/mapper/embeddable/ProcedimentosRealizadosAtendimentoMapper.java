package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ProcedimentosRealizadosAtendimentoRequest;
import com.upsaude.api.response.embeddable.ProcedimentosRealizadosAtendimentoResponse;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import com.upsaude.dto.embeddable.ProcedimentosRealizadosAtendimentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ProcedimentosRealizadosAtendimentoMapper {
    ProcedimentosRealizadosAtendimento toEntity(ProcedimentosRealizadosAtendimentoRequest request);
    ProcedimentosRealizadosAtendimentoResponse toResponse(ProcedimentosRealizadosAtendimento entity);
    void updateFromRequest(ProcedimentosRealizadosAtendimentoRequest request, @MappingTarget ProcedimentosRealizadosAtendimento entity);

    // Mapeamento entre DTO e Entity
    ProcedimentosRealizadosAtendimento toEntity(com.upsaude.dto.embeddable.ProcedimentosRealizadosAtendimentoDTO dto);
    com.upsaude.dto.embeddable.ProcedimentosRealizadosAtendimentoDTO toDTO(ProcedimentosRealizadosAtendimento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ProcedimentosRealizadosAtendimentoDTO dto, @MappingTarget ProcedimentosRealizadosAtendimento entity);
}
