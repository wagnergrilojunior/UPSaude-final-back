package com.upsaude.mapper;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import com.upsaude.dto.FabricantesVacinaDTO;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de FabricantesVacina.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class})
public interface FabricantesVacinaMapper extends EntityMapper<FabricantesVacina, FabricantesVacinaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    FabricantesVacina toEntity(FabricantesVacinaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    FabricantesVacinaDTO toDTO(FabricantesVacina entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    FabricantesVacina fromRequest(FabricantesVacinaRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(FabricantesVacinaRequest request, @MappingTarget FabricantesVacina entity);

    /**
     * Converte Entity para Response.
     */
    FabricantesVacinaResponse toResponse(FabricantesVacina entity);
}
