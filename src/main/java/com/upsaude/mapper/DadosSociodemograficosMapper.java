package com.upsaude.mapper;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.dto.DadosSociodemograficosDTO;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosSociodemograficosMapper extends EntityMapper<DadosSociodemograficos, DadosSociodemograficosDTO> {

    @Mapping(target = "active", ignore = true)
    DadosSociodemograficos toEntity(DadosSociodemograficosDTO dto);

    DadosSociodemograficosDTO toDTO(DadosSociodemograficos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DadosSociodemograficos fromRequest(DadosSociodemograficosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DadosSociodemograficosRequest request, @MappingTarget DadosSociodemograficos entity);

    @Mapping(target = "paciente", ignore = true)
    DadosSociodemograficosResponse toResponse(DadosSociodemograficos entity);
}
