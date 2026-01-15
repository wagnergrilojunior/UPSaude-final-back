package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.api.response.paciente.DadosSociodemograficosResponse;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface DadosSociodemograficosMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    DadosSociodemograficos fromRequest(DadosSociodemograficosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(DadosSociodemograficosRequest request, @MappingTarget DadosSociodemograficos entity);

    @Mapping(target = "paciente", ignore = true)
    DadosSociodemograficosResponse toResponse(DadosSociodemograficos entity);
}
