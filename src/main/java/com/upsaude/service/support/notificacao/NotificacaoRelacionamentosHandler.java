package com.upsaude.service.support.notificacao;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacaoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AgendamentoRepository agendamentoRepository;
    private final TemplateNotificacaoRepository templateNotificacaoRepository;

    public void resolver(Notificacao entity, NotificacaoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        }

        if (request.getAgendamento() != null) {
            Agendamento agendamento = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(agendamento);
        }

        if (request.getTemplate() != null) {
            TemplateNotificacao template = templateNotificacaoRepository.findByIdAndTenant(request.getTemplate(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Template de notificação não encontrado com ID: " + request.getTemplate()));
            entity.setTemplate(template);
        }
    }
}
