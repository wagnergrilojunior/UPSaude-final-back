package com.upsaude.mapper;

import com.upsaude.api.request.EquipamentosRequest;
import com.upsaude.api.response.EquipamentosResponse;
import com.upsaude.dto.EquipamentosDTO;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Equipamentos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {FabricantesEquipamentoMapper.class})
public interface EquipamentosMapper extends EntityMapper<Equipamentos, EquipamentosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Equipamentos toEntity(EquipamentosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EquipamentosDTO toDTO(Equipamentos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    Equipamentos fromRequest(EquipamentosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    void updateFromRequest(EquipamentosRequest request, @MappingTarget Equipamentos entity);

    /**
     * Converte Entity para Response.
     */
    EquipamentosResponse toResponse(Equipamentos entity);
}
