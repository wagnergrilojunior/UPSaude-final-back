package com.upsaude.mapper;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.dto.EspecialidadesMedicasDTO;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {com.upsaude.mapper.embeddable.ClassificacaoEspecialidadeMedicaMapper.class})
public interface EspecialidadesMedicasMapper extends EntityMapper<EspecialidadesMedicas, EspecialidadesMedicasDTO> {

    @Mapping(target = "active", ignore = true)
    EspecialidadesMedicas toEntity(EspecialidadesMedicasDTO dto);

    EspecialidadesMedicasDTO toDTO(EspecialidadesMedicas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    EspecialidadesMedicas fromRequest(EspecialidadesMedicasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(EspecialidadesMedicasRequest request, @MappingTarget EspecialidadesMedicas entity);

    EspecialidadesMedicasResponse toResponse(EspecialidadesMedicas entity);
}
