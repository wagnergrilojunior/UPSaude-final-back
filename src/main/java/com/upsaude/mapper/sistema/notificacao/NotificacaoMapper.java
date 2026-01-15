package com.upsaude.mapper.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse;
import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.format.DateTimeFormatter;

@Mapper(config = MappingConfig.class)
public interface NotificacaoMapper  {

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

    @Mapping(target = "estabelecimento", expression = "java(mapEstabelecimentoRef(entity.getEstabelecimento()))")
    @Mapping(target = "paciente", expression = "java(mapPacienteRef(entity.getPaciente()))")
    @Mapping(target = "profissional", expression = "java(mapProfissionalRef(entity.getProfissional()))")
    @Mapping(target = "agendamento", expression = "java(mapAgendamentoRef(entity.getAgendamento()))")
    @Mapping(target = "template", expression = "java(mapTemplateRef(entity.getTemplate()))")
    NotificacaoResponse toResponse(Notificacao entity);

    default NotificacaoReferenciaResponse.EstabelecimentoRef mapEstabelecimentoRef(Estabelecimentos estabelecimento) {
        if (estabelecimento == null) {
            return null;
        }
        String nome = estabelecimento.getDadosIdentificacao() != null 
            ? estabelecimento.getDadosIdentificacao().getNome() 
            : null;
        return NotificacaoReferenciaResponse.EstabelecimentoRef.builder()
            .id(estabelecimento.getId())
            .nome(nome)
            .build();
    }

    default NotificacaoReferenciaResponse.PacienteRef mapPacienteRef(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        String nome = paciente.getNomeCompleto();
        String email = null;
        if (paciente.getContatos() != null && !paciente.getContatos().isEmpty()) {
            email = paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                .map(PacienteContato::getEmail)
                .filter(e -> e != null && !e.trim().isEmpty())
                .findFirst()
                .orElse(null);
        }
        return NotificacaoReferenciaResponse.PacienteRef.builder()
            .id(paciente.getId())
            .nome(nome)
            .email(email)
            .build();
    }

    default NotificacaoReferenciaResponse.ProfissionalRef mapProfissionalRef(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        String nome = profissional.getDadosPessoaisBasicos() != null 
            ? profissional.getDadosPessoaisBasicos().getNomeCompleto() 
            : null;
        return NotificacaoReferenciaResponse.ProfissionalRef.builder()
            .id(profissional.getId())
            .nome(nome)
            .build();
    }

    default NotificacaoReferenciaResponse.AgendamentoRef mapAgendamentoRef(Agendamento agendamento) {
        if (agendamento == null) {
            return null;
        }
        String dataHora = agendamento.getDataHora() != null 
            ? agendamento.getDataHora().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) 
            : null;
        String status = agendamento.getStatus() != null 
            ? agendamento.getStatus().toString() 
            : null;
        return NotificacaoReferenciaResponse.AgendamentoRef.builder()
            .id(agendamento.getId())
            .dataHora(dataHora)
            .status(status)
            .build();
    }

    default NotificacaoReferenciaResponse.TemplateRef mapTemplateRef(TemplateNotificacao template) {
        if (template == null) {
            return null;
        }
        return NotificacaoReferenciaResponse.TemplateRef.builder()
            .id(template.getId())
            .nome(template.getNome())
            .brevoTemplateId(template.getBrevoTemplateId())
            .build();
    }
}
