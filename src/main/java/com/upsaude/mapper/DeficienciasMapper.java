package com.upsaude.mapper;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import com.upsaude.dto.DeficienciasDTO;
import com.upsaude.entity.Deficiencias;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Deficiências.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface DeficienciasMapper extends EntityMapper<Deficiencias, DeficienciasDTO> {

    @Mapping(target = "tenant", ignore = true)
    Deficiencias toEntity(DeficienciasDTO dto);

    DeficienciasDTO toDTO(Deficiencias entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Deficiencias fromRequest(DeficienciasRequest request);

    DeficienciasResponse toResponse(Deficiencias entity);
}

