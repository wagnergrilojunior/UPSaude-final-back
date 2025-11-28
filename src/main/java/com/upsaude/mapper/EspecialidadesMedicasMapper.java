package com.upsaude.mapper;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.dto.EspecialidadesMedicasDTO;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface EspecialidadesMedicasMapper extends EntityMapper<EspecialidadesMedicas, EspecialidadesMedicasDTO> {

    @Mapping(target = "tenant", ignore = true)
    EspecialidadesMedicas toEntity(EspecialidadesMedicasDTO dto);

    EspecialidadesMedicasDTO toDTO(EspecialidadesMedicas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    EspecialidadesMedicas fromRequest(EspecialidadesMedicasRequest request);

    EspecialidadesMedicasResponse toResponse(EspecialidadesMedicas entity);
}
