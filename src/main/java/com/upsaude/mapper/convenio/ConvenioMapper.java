package com.upsaude.mapper.convenio;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class, com.upsaude.mapper.embeddable.ContatoConvenioMapper.class, com.upsaude.mapper.embeddable.RegistroANSConvenioMapper.class, com.upsaude.mapper.embeddable.CoberturaConvenioMapper.class, com.upsaude.mapper.embeddable.InformacoesFinanceirasConvenioMapper.class, com.upsaude.mapper.embeddable.IntegracaoGovernamentalConvenioMapper.class})
public interface ConvenioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Convenio fromRequest(ConvenioRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(ConvenioRequest request, @MappingTarget Convenio entity);

    ConvenioResponse toResponse(Convenio entity);
}
