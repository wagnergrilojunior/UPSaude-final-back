package com.upsaude.mapper;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.dto.MovimentacoesEstoqueDTO;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.EstoquesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de MovimentacoesEstoque.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstoquesVacinaMapper.class})
public interface MovimentacoesEstoqueMapper extends EntityMapper<MovimentacoesEstoque, MovimentacoesEstoqueDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    MovimentacoesEstoque toEntity(MovimentacoesEstoqueDTO dto);

    /**
     * Converte Entity para DTO.
     */
    MovimentacoesEstoqueDTO toDTO(MovimentacoesEstoque entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estoqueVacina", ignore = true)
    MovimentacoesEstoque fromRequest(MovimentacoesEstoqueRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estoqueVacina", ignore = true)
    void updateFromRequest(MovimentacoesEstoqueRequest request, @MappingTarget MovimentacoesEstoque entity);

    /**
     * Converte Entity para Response.
     */
    MovimentacoesEstoqueResponse toResponse(MovimentacoesEstoque entity);
}
