package com.upsaude.mapper;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.dto.DadosClinicosBasicosDTO;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de DadosClinicosBasicos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface DadosClinicosBasicosMapper extends EntityMapper<DadosClinicosBasicos, DadosClinicosBasicosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    DadosClinicosBasicos toEntity(DadosClinicosBasicosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    DadosClinicosBasicosDTO toDTO(DadosClinicosBasicos entity);

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
    DadosClinicosBasicos fromRequest(DadosClinicosBasicosRequest request);

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
    void updateFromRequest(DadosClinicosBasicosRequest request, @MappingTarget DadosClinicosBasicos entity);

    /**
     * Converte Entity para Response.
     */
    DadosClinicosBasicosResponse toResponse(DadosClinicosBasicos entity);
}
