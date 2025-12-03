package com.upsaude.mapper;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.dto.DispensacoesMedicamentosDTO;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de DispensacoesMedicamentos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {MedicacaoMapper.class, PacienteMapper.class})
public interface DispensacoesMedicamentosMapper extends EntityMapper<DispensacoesMedicamentos, DispensacoesMedicamentosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    DispensacoesMedicamentos toEntity(DispensacoesMedicamentosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    DispensacoesMedicamentosDTO toDTO(DispensacoesMedicamentos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DispensacoesMedicamentos fromRequest(DispensacoesMedicamentosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DispensacoesMedicamentosRequest request, @MappingTarget DispensacoesMedicamentos entity);

    /**
     * Converte Entity para Response.
     */
    DispensacoesMedicamentosResponse toResponse(DispensacoesMedicamentos entity);
}
