package com.upsaude.mapper;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import com.upsaude.dto.VisitasDomiciliaresDTO;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de VisitasDomiciliares.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface VisitasDomiciliaresMapper extends EntityMapper<VisitasDomiciliares, VisitasDomiciliaresDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    VisitasDomiciliares toEntity(VisitasDomiciliaresDTO dto);

    /**
     * Converte Entity para DTO.
     */
    VisitasDomiciliaresDTO toDTO(VisitasDomiciliares entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    VisitasDomiciliares fromRequest(VisitasDomiciliaresRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(VisitasDomiciliaresRequest request, @MappingTarget VisitasDomiciliares entity);

    /**
     * Converte Entity para Response.
     */
    VisitasDomiciliaresResponse toResponse(VisitasDomiciliares entity);
}
