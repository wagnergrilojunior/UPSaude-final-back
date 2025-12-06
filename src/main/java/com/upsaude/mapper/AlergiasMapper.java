package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.dto.AlergiasDTO;
import com.upsaude.entity.Alergias;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ClassificacaoAlergiaMapper;
import com.upsaude.mapper.embeddable.PrevencaoTratamentoAlergiaMapper;
import com.upsaude.mapper.embeddable.ReacoesAlergiaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Alergias.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ClassificacaoAlergiaMapper.class, ReacoesAlergiaMapper.class, PrevencaoTratamentoAlergiaMapper.class})
public interface AlergiasMapper extends EntityMapper<Alergias, AlergiasDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Alergias toEntity(AlergiasDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AlergiasDTO toDTO(Alergias entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Alergias fromRequest(AlergiasRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(AlergiasRequest request, @MappingTarget Alergias entity);

    /**
     * Converte Entity para Response.
     */
    AlergiasResponse toResponse(Alergias entity);
}
