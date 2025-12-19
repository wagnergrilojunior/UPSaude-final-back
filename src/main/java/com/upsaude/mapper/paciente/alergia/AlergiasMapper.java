package com.upsaude.mapper.paciente.alergia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.api.response.alergia.AlergiasResponse;
import com.upsaude.entity.paciente.alergia.Alergias;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ClassificacaoAlergiaMapper;
import com.upsaude.mapper.embeddable.PrevencaoTratamentoAlergiaMapper;
import com.upsaude.mapper.embeddable.ReacoesAlergiaMapper;

@Mapper(config = MappingConfig.class, uses = {ClassificacaoAlergiaMapper.class, ReacoesAlergiaMapper.class, PrevencaoTratamentoAlergiaMapper.class})
public interface AlergiasMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Alergias fromRequest(AlergiasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(AlergiasRequest request, @MappingTarget Alergias entity);

    AlergiasResponse toResponse(Alergias entity);
}
