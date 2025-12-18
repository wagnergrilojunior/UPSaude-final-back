package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.EspecialidadesMedicasRequest;
import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

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
