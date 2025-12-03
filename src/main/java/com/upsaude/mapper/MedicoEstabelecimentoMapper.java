package com.upsaude.mapper;

import com.upsaude.api.request.MedicoEstabelecimentoRequest;
import com.upsaude.api.response.MedicoEstabelecimentoResponse;
import com.upsaude.dto.MedicoEstabelecimentoDTO;
import com.upsaude.entity.MedicoEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de MedicoEstabelecimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, MedicosMapper.class})
public interface MedicoEstabelecimentoMapper extends EntityMapper<MedicoEstabelecimento, MedicoEstabelecimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    MedicoEstabelecimento toEntity(MedicoEstabelecimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    MedicoEstabelecimentoDTO toDTO(MedicoEstabelecimento entity);

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
    @Mapping(target = "medico", ignore = true)
    MedicoEstabelecimento fromRequest(MedicoEstabelecimentoRequest request);

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
    @Mapping(target = "medico", ignore = true)
    void updateFromRequest(MedicoEstabelecimentoRequest request, @MappingTarget MedicoEstabelecimento entity);

    /**
     * Converte Entity para Response.
     */
    MedicoEstabelecimentoResponse toResponse(MedicoEstabelecimento entity);
}
