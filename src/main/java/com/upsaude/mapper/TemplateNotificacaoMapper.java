package com.upsaude.mapper;

import com.upsaude.api.request.TemplateNotificacaoRequest;
import com.upsaude.api.response.TemplateNotificacaoResponse;
import com.upsaude.dto.TemplateNotificacaoDTO;
import com.upsaude.entity.TemplateNotificacao;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de TemplateNotificacao.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface TemplateNotificacaoMapper extends EntityMapper<TemplateNotificacao, TemplateNotificacaoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    TemplateNotificacao toEntity(TemplateNotificacaoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    TemplateNotificacaoDTO toDTO(TemplateNotificacao entity);

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
    TemplateNotificacao fromRequest(TemplateNotificacaoRequest request);

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
    void updateFromRequest(TemplateNotificacaoRequest request, @MappingTarget TemplateNotificacao entity);

    /**
     * Converte Entity para Response.
     */
    TemplateNotificacaoResponse toResponse(TemplateNotificacao entity);
}
