package com.upsaude.mapper;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.dto.EstabelecimentosDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Estabelecimentos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class, ProfissionaisSaudeMapper.class})
public interface EstabelecimentosMapper extends EntityMapper<Estabelecimentos, EstabelecimentosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EstabelecimentosDTO toDTO(Estabelecimentos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoPrincipal", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    @Mapping(target = "responsavelTecnico", ignore = true)
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoPrincipal", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    @Mapping(target = "responsavelTecnico", ignore = true)
    void updateFromRequest(EstabelecimentosRequest request, @MappingTarget Estabelecimentos entity);

    /**
     * Converte Entity para Response.
     */
    EstabelecimentosResponse toResponse(Estabelecimentos entity);
}
