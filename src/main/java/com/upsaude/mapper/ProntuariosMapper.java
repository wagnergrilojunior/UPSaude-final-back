package com.upsaude.mapper;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.dto.ProntuariosDTO;
import com.upsaude.entity.Prontuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ProntuariosMapper extends EntityMapper<Prontuarios, ProntuariosDTO> {

    @Mapping(target = "tenant", ignore = true)
    Prontuarios toEntity(ProntuariosDTO dto);

    ProntuariosDTO toDTO(Prontuarios entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Prontuarios fromRequest(ProntuariosRequest request);

    ProntuariosResponse toResponse(Prontuarios entity);
}

