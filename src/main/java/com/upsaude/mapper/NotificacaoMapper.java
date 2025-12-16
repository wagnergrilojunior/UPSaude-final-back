package com.upsaude.mapper;

import com.upsaude.api.request.NotificacaoRequest;
import com.upsaude.api.response.NotificacaoResponse;
import com.upsaude.dto.NotificacaoDTO;
import com.upsaude.entity.Notificacao;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, TemplateNotificacaoMapper.class, EstabelecimentosMapper.class})
public interface NotificacaoMapper extends EntityMapper<Notificacao, NotificacaoDTO> {

    @Mapping(target = "active", ignore = true)
    Notificacao toEntity(NotificacaoDTO dto);

    NotificacaoDTO toDTO(Notificacao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "template", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    Notificacao fromRequest(NotificacaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "template", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(NotificacaoRequest request, @MappingTarget Notificacao entity);

    NotificacaoResponse toResponse(Notificacao entity);
}
