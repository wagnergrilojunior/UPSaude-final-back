package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ProcedimentosRealizadosAtendimentoRequest;
import com.upsaude.api.response.embeddable.ProcedimentosRealizadosAtendimentoResponse;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ProcedimentosRealizadosAtendimentoMapper {
    ProcedimentosRealizadosAtendimento toEntity(ProcedimentosRealizadosAtendimentoRequest request);
    ProcedimentosRealizadosAtendimentoResponse toResponse(ProcedimentosRealizadosAtendimento entity);
    void updateFromRequest(ProcedimentosRealizadosAtendimentoRequest request, @MappingTarget ProcedimentosRealizadosAtendimento entity);

}
