package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.MedicoEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.MedicoEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface MedicoEstabelecimentoMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    MedicoEstabelecimento fromRequest(MedicoEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    void updateFromRequest(MedicoEstabelecimentoRequest request, @MappingTarget MedicoEstabelecimento entity);

    @Mapping(target = "medico", ignore = true)
    MedicoEstabelecimentoResponse toResponse(MedicoEstabelecimento entity);
}
