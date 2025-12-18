package com.upsaude.service.support.consultas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultasRequest;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.profissional.EspecialidadesMedicasRepository;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultasRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final ConvenioRepository convenioRepository;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

    public void resolver(Consultas entity, ConsultasRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID pacienteId = Objects.requireNonNull(request.getPaciente(), "paciente é obrigatório");
        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(pacienteId, tenantId));

        if (request.getMedico() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
            entity.setMedico(medico);
        } else {
            entity.setMedico(null);
        }

        if (request.getProfissionalSaude() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalSaude(), tenantId);
            entity.setProfissionalSaude(profissional);
        } else {
            entity.setProfissionalSaude(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }

        if (request.getEspecialidade() != null) {
            UUID especialidadeId = Objects.requireNonNull(request.getEspecialidade(), "especialidade");
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(especialidadeId)
                .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + especialidadeId));
            entity.setEspecialidade(especialidade);
        } else {
            entity.setEspecialidade(null);
        }

        // CidPrincipal removido - CidDoencas foi deletado

        if (entity.getProfissionalSaude() != null && entity.getProfissionalSaude().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissionalSaude().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
