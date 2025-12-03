package com.upsaude.mapper;

import com.upsaude.api.request.AtividadeProfissionalRequest;
import com.upsaude.api.response.AtividadeProfissionalResponse;
import com.upsaude.dto.AtividadeProfissionalDTO;
import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de AtividadeProfissional.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, CirurgiaMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface AtividadeProfissionalMapper extends EntityMapper<AtividadeProfissional, AtividadeProfissionalDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    AtividadeProfissional toEntity(AtividadeProfissionalDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AtividadeProfissionalDTO toDTO(AtividadeProfissional entity);

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
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    AtividadeProfissional fromRequest(AtividadeProfissionalRequest request);

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
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtividadeProfissionalRequest request, @MappingTarget AtividadeProfissional entity);

    /**
     * Converte Entity para Response.
     */
    AtividadeProfissionalResponse toResponse(AtividadeProfissional entity);
}
