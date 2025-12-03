package com.upsaude.mapper;

import com.upsaude.api.request.UsuarioEstabelecimentoRequest;
import com.upsaude.api.response.UsuarioEstabelecimentoResponse;
import com.upsaude.dto.UsuarioEstabelecimentoDTO;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de UsuarioEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, UsuariosSistemaMapper.class})
public interface UsuarioEstabelecimentoMapper extends EntityMapper<UsuarioEstabelecimento, UsuarioEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    UsuarioEstabelecimento toEntity(UsuarioEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    UsuarioEstabelecimentoDTO toDTO(UsuarioEstabelecimento entity);

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
    @Mapping(target = "usuario", ignore = true)
    UsuarioEstabelecimento fromRequest(UsuarioEstabelecimentoRequest request);

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
    @Mapping(target = "usuario", ignore = true)
    void updateFromRequest(UsuarioEstabelecimentoRequest request, @MappingTarget UsuarioEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    UsuarioEstabelecimentoResponse toResponse(UsuarioEstabelecimento entity);
}
