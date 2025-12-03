package com.upsaude.mapper;

import com.upsaude.api.request.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.ServicosEstabelecimentoResponse;
import com.upsaude.dto.ServicosEstabelecimentoDTO;
import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ServicosEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface ServicosEstabelecimentoMapper extends EntityMapper<ServicosEstabelecimento, ServicosEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ServicosEstabelecimento toEntity(ServicosEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ServicosEstabelecimentoDTO toDTO(ServicosEstabelecimento entity);

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
    ServicosEstabelecimento fromRequest(ServicosEstabelecimentoRequest request);

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
    void updateFromRequest(ServicosEstabelecimentoRequest request, @MappingTarget ServicosEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    ServicosEstabelecimentoResponse toResponse(ServicosEstabelecimento entity);
}
