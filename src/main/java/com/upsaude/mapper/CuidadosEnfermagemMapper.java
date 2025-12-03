package com.upsaude.mapper;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.dto.CuidadosEnfermagemDTO;
import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de CuidadosEnfermagem.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface CuidadosEnfermagemMapper extends EntityMapper<CuidadosEnfermagem, CuidadosEnfermagemDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    CuidadosEnfermagem toEntity(CuidadosEnfermagemDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CuidadosEnfermagemDTO toDTO(CuidadosEnfermagem entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    CuidadosEnfermagem fromRequest(CuidadosEnfermagemRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(CuidadosEnfermagemRequest request, @MappingTarget CuidadosEnfermagem entity);

    /**
     * Converte Entity para Response.
     */
    CuidadosEnfermagemResponse toResponse(CuidadosEnfermagem entity);
}
