package com.upsaude.mapper;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.dto.DadosSociodemograficosDTO;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de DadosSociodemograficos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface DadosSociodemograficosMapper extends EntityMapper<DadosSociodemograficos, DadosSociodemograficosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    DadosSociodemograficos toEntity(DadosSociodemograficosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    DadosSociodemograficosDTO toDTO(DadosSociodemograficos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DadosSociodemograficos fromRequest(DadosSociodemograficosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DadosSociodemograficosRequest request, @MappingTarget DadosSociodemograficos entity);

    /**
     * Converte Entity para Response.
     */
    DadosSociodemograficosResponse toResponse(DadosSociodemograficos entity);
}
