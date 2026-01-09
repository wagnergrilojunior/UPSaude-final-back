package com.upsaude.service.api.support.agendamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final ConvenioRepository convenioRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final SigtapCboRepository sigtapCboRepository;

    public void resolver(Agendamento entity, AgendamentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null)
            return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(),
                    tenantId);
            entity.setProfissional(profissional);
            if (profissional.getEstabelecimento() != null) {
                entity.setEstabelecimento(profissional.getEstabelecimento());
            }
        }

        if (request.getMedico() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
            entity.setMedico(medico);
        }

        if (request.getEspecialidade() != null) {
            SigtapOcupacao especialidade = sigtapCboRepository.findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException(
                            "Especialidade não encontrada com ID: " + request.getEspecialidade()));
            entity.setEspecialidade(especialidade);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(
                            () -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        }

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new NotFoundException(
                            "Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(atendimento);
        }

        if (request.getAgendamentoOriginal() != null) {
            Agendamento agendamentoOriginal = agendamentoRepository
                    .findByIdAndTenant(request.getAgendamentoOriginal(), tenantId)
                    .orElseThrow(() -> new NotFoundException(
                            "Agendamento original não encontrado com ID: " + request.getAgendamentoOriginal()));
            entity.setAgendamentoOriginal(agendamentoOriginal);
        }
    }
}
