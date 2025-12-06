package com.upsaude.mapper;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.dto.EspecialidadesMedicasDTO;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EspecialidadesMedicas.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {com.upsaude.mapper.embeddable.ClassificacaoEspecialidadeMedicaMapper.class})
public interface EspecialidadesMedicasMapper extends EntityMapper<EspecialidadesMedicas, EspecialidadesMedicasDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EspecialidadesMedicas toEntity(EspecialidadesMedicasDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EspecialidadesMedicasDTO toDTO(EspecialidadesMedicas entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    EspecialidadesMedicas fromRequest(EspecialidadesMedicasRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(EspecialidadesMedicasRequest request, @MappingTarget EspecialidadesMedicas entity);

    /**
     * Converte Entity para Response.
     */
    EspecialidadesMedicasResponse toResponse(EspecialidadesMedicas entity);
}
