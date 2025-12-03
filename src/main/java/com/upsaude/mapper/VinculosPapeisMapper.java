package com.upsaude.mapper;

import com.upsaude.api.request.VinculosPapeisRequest;
import com.upsaude.api.response.VinculosPapeisResponse;
import com.upsaude.dto.VinculosPapeisDTO;
import com.upsaude.entity.VinculosPapeis;
import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Papeis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de VinculosPapeis.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {DepartamentosMapper.class, EstabelecimentosMapper.class, PapeisMapper.class})
public interface VinculosPapeisMapper extends EntityMapper<VinculosPapeis, VinculosPapeisDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    VinculosPapeis toEntity(VinculosPapeisDTO dto);

    /**
     * Converte Entity para DTO.
     */
    VinculosPapeisDTO toDTO(VinculosPapeis entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "papel", ignore = true)
    VinculosPapeis fromRequest(VinculosPapeisRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "departamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "papel", ignore = true)
    void updateFromRequest(VinculosPapeisRequest request, @MappingTarget VinculosPapeis entity);

    /**
     * Converte Entity para Response.
     */
    VinculosPapeisResponse toResponse(VinculosPapeis entity);
}
