package com.upsaude.mapper;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.EquipamentosEstabelecimentoResponse;
import com.upsaude.dto.EquipamentosEstabelecimentoDTO;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EquipamentosEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EquipamentosMapper.class, EstabelecimentosMapper.class})
public interface EquipamentosEstabelecimentoMapper extends EntityMapper<EquipamentosEstabelecimento, EquipamentosEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EquipamentosEstabelecimento toEntity(EquipamentosEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EquipamentosEstabelecimentoDTO toDTO(EquipamentosEstabelecimento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    EquipamentosEstabelecimento fromRequest(EquipamentosEstabelecimentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(EquipamentosEstabelecimentoRequest request, @MappingTarget EquipamentosEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    EquipamentosEstabelecimentoResponse toResponse(EquipamentosEstabelecimento entity);
}
