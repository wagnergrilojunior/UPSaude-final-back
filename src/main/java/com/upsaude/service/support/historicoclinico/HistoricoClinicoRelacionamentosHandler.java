package com.upsaude.service.support.historicoclinico;

import com.upsaude.api.request.clinica.prontuario.HistoricoClinicoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.clinica.exame.Exames;
import com.upsaude.entity.clinica.medicacao.ReceitasMedicas;
import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.cirurgia.CirurgiaRepository;
import com.upsaude.repository.clinica.exame.ExamesRepository;
import com.upsaude.repository.clinica.medicacao.ReceitasMedicasRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricoClinicoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final ExamesRepository examesRepository;
    private final ReceitasMedicasRepository receitasMedicasRepository;
    private final CirurgiaRepository cirurgiaRepository;

    public void resolver(HistoricoClinico entity, HistoricoClinicoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        }

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(atendimento);
        }

        if (request.getAgendamento() != null) {
            Agendamento agendamento = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(agendamento);
        }

        if (request.getExame() != null) {
            Exames exame = examesRepository.findByIdAndTenant(request.getExame(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Exame não encontrado com ID: " + request.getExame()));
            entity.setExame(exame);
        }

        if (request.getReceita() != null) {
            ReceitasMedicas receita = receitasMedicasRepository.findByIdAndTenant(request.getReceita(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Receita médica não encontrada com ID: " + request.getReceita()));
            entity.setReceita(receita);
        }

        if (request.getCirurgia() != null) {
            Cirurgia cirurgia = cirurgiaRepository.findByIdAndTenant(request.getCirurgia(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Cirurgia não encontrada com ID: " + request.getCirurgia()));
            entity.setCirurgia(cirurgia);
        }
    }
}
