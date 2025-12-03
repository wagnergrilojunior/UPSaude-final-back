package com.upsaude.mapper;

import com.upsaude.api.request.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.dto.ConfiguracaoEstabelecimentoDTO;
import com.upsaude.entity.ConfiguracaoEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ConfiguracaoEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface ConfiguracaoEstabelecimentoMapper extends EntityMapper<ConfiguracaoEstabelecimento, ConfiguracaoEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ConfiguracaoEstabelecimento toEntity(ConfiguracaoEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ConfiguracaoEstabelecimentoDTO toDTO(ConfiguracaoEstabelecimento entity);

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
    ConfiguracaoEstabelecimento fromRequest(ConfiguracaoEstabelecimentoRequest request);

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
    void updateFromRequest(ConfiguracaoEstabelecimentoRequest request, @MappingTarget ConfiguracaoEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    ConfiguracaoEstabelecimentoResponse toResponse(ConfiguracaoEstabelecimento entity);
}
