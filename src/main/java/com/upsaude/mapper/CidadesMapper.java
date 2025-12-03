package com.upsaude.mapper;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.dto.CidadesDTO;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Cidades.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstadosMapper.class})
public interface CidadesMapper extends EntityMapper<Cidades, CidadesDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Cidades toEntity(CidadesDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CidadesDTO toDTO(Cidades entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cidades fromRequest(CidadesRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(CidadesRequest request, @MappingTarget Cidades entity);

    /**
     * Converte Entity para Response.
     */
    CidadesResponse toResponse(Cidades entity);
}
