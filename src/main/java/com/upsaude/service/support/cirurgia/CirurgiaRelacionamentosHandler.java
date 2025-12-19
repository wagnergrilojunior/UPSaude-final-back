package com.upsaude.service.support.cirurgia;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CirurgiaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final ConvenioRepository convenioRepository;

    public Cirurgia processarRelacionamentos(Cirurgia entity, CirurgiaRequest request, UUID tenantId) {
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        ProfissionaisSaude cirurgiaoPrincipal = profissionaisSaudeTenantEnforcer.validarAcesso(request.getCirurgiaoPrincipal(), tenantId);
        entity.setCirurgiaoPrincipal(cirurgiaoPrincipal);

        if (request.getMedicoCirurgiao() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedicoCirurgiao(), tenantId);
            entity.setMedicoCirurgiao(medico);
        } else {
            entity.setMedicoCirurgiao(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }


        return entity;
    }
}

