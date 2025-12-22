package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ProfissionalEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ProfissionalEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, ProfissionaisSaudeMapper.class})
public interface ProfissionalEstabelecimentoMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ProfissionalEstabelecimento fromRequest(ProfissionalEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(ProfissionalEstabelecimentoRequest request, @MappingTarget ProfissionalEstabelecimento entity);

    ProfissionalEstabelecimentoResponse toResponse(ProfissionalEstabelecimento entity);
}
