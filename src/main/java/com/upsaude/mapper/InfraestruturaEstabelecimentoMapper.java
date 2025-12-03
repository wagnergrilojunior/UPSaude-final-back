package com.upsaude.mapper;

import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.InfraestruturaEstabelecimentoResponse;
import com.upsaude.dto.InfraestruturaEstabelecimentoDTO;
import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de InfraestruturaEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface InfraestruturaEstabelecimentoMapper extends EntityMapper<InfraestruturaEstabelecimento, InfraestruturaEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    InfraestruturaEstabelecimento toEntity(InfraestruturaEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    InfraestruturaEstabelecimentoDTO toDTO(InfraestruturaEstabelecimento entity);

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
    InfraestruturaEstabelecimento fromRequest(InfraestruturaEstabelecimentoRequest request);

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
    void updateFromRequest(InfraestruturaEstabelecimentoRequest request, @MappingTarget InfraestruturaEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    InfraestruturaEstabelecimentoResponse toResponse(InfraestruturaEstabelecimento entity);
}
