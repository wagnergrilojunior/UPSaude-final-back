package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.ProfissionalEstabelecimentoResponse;
import com.upsaude.dto.ProfissionalEstabelecimentoDTO;
import com.upsaude.entity.ProfissionalEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ProfissionalEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, ProfissionaisSaudeMapper.class})
public interface ProfissionalEstabelecimentoMapper extends EntityMapper<ProfissionalEstabelecimento, ProfissionalEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ProfissionalEstabelecimento toEntity(ProfissionalEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ProfissionalEstabelecimentoDTO toDTO(ProfissionalEstabelecimento entity);

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
    @Mapping(target = "profissional", ignore = true)
    ProfissionalEstabelecimento fromRequest(ProfissionalEstabelecimentoRequest request);

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
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(ProfissionalEstabelecimentoRequest request, @MappingTarget ProfissionalEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    ProfissionalEstabelecimentoResponse toResponse(ProfissionalEstabelecimento entity);
}
