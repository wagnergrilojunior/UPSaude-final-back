package com.upsaude.mapper;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.dto.AgendamentoDTO;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Agendamento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface AgendamentoMapper extends EntityMapper<Agendamento, AgendamentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Agendamento toEntity(AgendamentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AgendamentoDTO toDTO(Agendamento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    Agendamento fromRequest(AgendamentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    void updateFromRequest(AgendamentoRequest request, @MappingTarget Agendamento entity);

    /**
     * Converte Entity para Response.
     */
    AgendamentoResponse toResponse(Agendamento entity);
}
