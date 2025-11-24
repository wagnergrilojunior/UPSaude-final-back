package com.upsaude.mapper;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.dto.ConsultasDTO;
import com.upsaude.entity.Consultas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ConsultasMapper extends EntityMapper<Consultas, ConsultasDTO> {

    @Mapping(target = "tenant", ignore = true)
    Consultas toEntity(ConsultasDTO dto);

    ConsultasDTO toDTO(Consultas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Consultas fromRequest(ConsultasRequest request);

    ConsultasResponse toResponse(Consultas entity);
}

