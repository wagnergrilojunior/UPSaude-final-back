package com.upsaude.mapper;

import com.upsaude.api.request.CheckInAtendimentoRequest;
import com.upsaude.api.response.CheckInAtendimentoResponse;
import com.upsaude.dto.CheckInAtendimentoDTO;
import com.upsaude.entity.CheckInAtendimento;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de CheckInAtendimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, AtendimentoMapper.class, PacienteMapper.class})
public interface CheckInAtendimentoMapper extends EntityMapper<CheckInAtendimento, CheckInAtendimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    CheckInAtendimento toEntity(CheckInAtendimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CheckInAtendimentoDTO toDTO(CheckInAtendimento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    CheckInAtendimento fromRequest(CheckInAtendimentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(CheckInAtendimentoRequest request, @MappingTarget CheckInAtendimento entity);

    /**
     * Converte Entity para Response.
     */
    CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);
}
