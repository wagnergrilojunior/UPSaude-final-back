package com.upsaude.mapper.convenio;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ConvenioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Convenio fromRequest(ConvenioRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ConvenioRequest request, @MappingTarget Convenio entity);

    @Mapping(target = "tenantId", expression = "java(entity.getTenantId() != null ? entity.getTenantId() : (entity.getTenant() != null ? entity.getTenant().getId() : null))")
    @Mapping(target = "estabelecimentoId", expression = "java(entity.getEstabelecimentoId() != null ? entity.getEstabelecimentoId() : (entity.getEstabelecimento() != null ? entity.getEstabelecimento().getId() : null))")
    ConvenioResponse toResponse(Convenio entity);
}
