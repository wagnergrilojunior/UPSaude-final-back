package com.upsaude.service.support.historicoclinico;

import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.entity.exame.Exames;
import com.upsaude.entity.prontuario.HistoricoClinico;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.medicacao.ReceitasMedicas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.agendamento.AgendamentoTenantEnforcer;
import com.upsaude.service.support.atendimento.AtendimentoTenantEnforcer;
import com.upsaude.service.support.cirurgia.CirurgiaTenantEnforcer;
import com.upsaude.service.support.exames.ExamesTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricoClinicoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AtendimentoTenantEnforcer atendimentoTenantEnforcer;
    private final AgendamentoTenantEnforcer agendamentoTenantEnforcer;
    private final ExamesTenantEnforcer examesTenantEnforcer;
    private final ReceitasMedicasTenantEnforcer receitasMedicasTenantEnforcer;
    private final CirurgiaTenantEnforcer cirurgiaTenantEnforcer;

    public void resolver(HistoricoClinico entity, HistoricoClinicoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(Objects.requireNonNull(request.getPaciente(), "paciente"), tenantId);
        entity.setPaciente(paciente);

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        } else {
            entity.setProfissional(null);
        }

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoTenantEnforcer.validarAcesso(request.getAtendimento(), tenantId);
            entity.setAtendimento(atendimento);
        } else {
            entity.setAtendimento(null);
        }

        if (request.getAgendamento() != null) {
            Agendamento agendamento = agendamentoTenantEnforcer.validarAcesso(request.getAgendamento(), tenantId);
            entity.setAgendamento(agendamento);
        } else {
            entity.setAgendamento(null);
        }

        if (request.getExame() != null) {
            Exames exame = examesTenantEnforcer.validarAcesso(request.getExame(), tenantId);
            entity.setExame(exame);
        } else {
            entity.setExame(null);
        }

        if (request.getReceita() != null) {
            ReceitasMedicas receita = receitasMedicasTenantEnforcer.validarAcesso(request.getReceita(), tenantId);
            entity.setReceita(receita);
        } else {
            entity.setReceita(null);
        }

        if (request.getCirurgia() != null) {
            Cirurgia cirurgia = cirurgiaTenantEnforcer.validarAcesso(request.getCirurgia(), tenantId);
            entity.setCirurgia(cirurgia);
        } else {
            entity.setCirurgia(null);
        }

        if (entity.getAtendimento() != null && entity.getAtendimento().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getAtendimento().getEstabelecimento());
        } else if (entity.getAgendamento() != null && entity.getAgendamento().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getAgendamento().getEstabelecimento());
        } else if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
