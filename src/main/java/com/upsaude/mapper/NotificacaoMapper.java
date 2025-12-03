package com.upsaude.mapper;

import com.upsaude.api.request.NotificacaoRequest;
import com.upsaude.api.response.NotificacaoResponse;
import com.upsaude.dto.NotificacaoDTO;
import com.upsaude.entity.Notificacao;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.TemplateNotificacao;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Notificacao.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, TemplateNotificacaoMapper.class})
public interface NotificacaoMapper extends EntityMapper<Notificacao, NotificacaoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Notificacao toEntity(NotificacaoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    NotificacaoDTO toDTO(Notificacao entity);

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
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "template", ignore = true)
    Notificacao fromRequest(NotificacaoRequest request);

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
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "template", ignore = true)
    void updateFromRequest(NotificacaoRequest request, @MappingTarget Notificacao entity);

    /**
     * Converte Entity para Response.
     */
    NotificacaoResponse toResponse(Notificacao entity);
}
