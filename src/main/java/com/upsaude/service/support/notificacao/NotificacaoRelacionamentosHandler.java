package com.upsaude.service.support.notificacao;

import com.upsaude.api.request.notificacao.NotificacaoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.notificacao.Notificacao;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.agendamento.AgendamentoTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.support.templatenotificacao.TemplateNotificacaoTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacaoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AgendamentoTenantEnforcer agendamentoTenantEnforcer;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final TemplateNotificacaoTenantEnforcer templateNotificacaoTenantEnforcer;

    public void resolver(Notificacao entity, NotificacaoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        } else {
            entity.setPaciente(null);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        } else {
            entity.setProfissional(null);
        }

        if (request.getAgendamento() != null) {
            Agendamento agendamento = agendamentoTenantEnforcer.validarAcesso(request.getAgendamento(), tenantId);
            entity.setAgendamento(agendamento);
        } else {
            entity.setAgendamento(null);
        }

        if (request.getTemplate() != null) {
            TemplateNotificacao template = templateNotificacaoTenantEnforcer.validarAcesso(request.getTemplate(), tenantId);
            entity.setTemplate(template);
        } else {
            entity.setTemplate(null);
        }

        // Estabelecimento: request -> agendamento -> profissional -> template
        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
            return;
        }

        if (entity.getAgendamento() != null && entity.getAgendamento().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getAgendamento().getEstabelecimento());
            return;
        }

        if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
            return;
        }

        if (entity.getTemplate() != null && entity.getTemplate().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getTemplate().getEstabelecimento());
            return;
        }

        // Mantém nulo: mas só permite se o tenant não exige estabelecimento em notificações.
        entity.setEstabelecimento(null);
    }
}

