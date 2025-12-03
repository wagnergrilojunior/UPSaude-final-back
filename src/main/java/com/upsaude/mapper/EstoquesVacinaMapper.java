package com.upsaude.mapper;

import com.upsaude.api.request.EstoquesVacinaRequest;
import com.upsaude.api.response.EstoquesVacinaResponse;
import com.upsaude.dto.EstoquesVacinaDTO;
import com.upsaude.entity.EstoquesVacina;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EstoquesVacina.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, VacinasMapper.class})
public interface EstoquesVacinaMapper extends EntityMapper<EstoquesVacina, EstoquesVacinaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EstoquesVacina toEntity(EstoquesVacinaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EstoquesVacinaDTO toDTO(EstoquesVacina entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    EstoquesVacina fromRequest(EstoquesVacinaRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    void updateFromRequest(EstoquesVacinaRequest request, @MappingTarget EstoquesVacina entity);

    /**
     * Converte Entity para Response.
     */
    EstoquesVacinaResponse toResponse(EstoquesVacina entity);
}
