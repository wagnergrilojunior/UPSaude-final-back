package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.response.embeddable.AnamneseAtendimentoResponse;
import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.mapper.clinica.atendimento.SinalVitalMapper;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { SinalVitalMapper.class })
public interface AnamneseAtendimentoMapper {
    AnamneseAtendimento toEntity(AnamneseAtendimentoRequest request);

    AnamneseAtendimentoResponse toResponse(AnamneseAtendimento entity);

    void updateFromRequest(AnamneseAtendimentoRequest request, @MappingTarget AnamneseAtendimento entity);
}
