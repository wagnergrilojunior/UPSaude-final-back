package com.upsaude.service.api.support.atendimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.service.api.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtendimentoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;
    private final ConvenioRepository convenioRepository;

    public void resolver(Atendimento entity, AtendimentoRequest request, UUID tenantId, Tenant tenant) {
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


        if (request.getEquipeSaude() != null) {
            EquipeSaude equipeSaude = equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId);
            entity.setEquipeSaude(equipeSaude);
        } else {
            entity.setEquipeSaude(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }

        // CidPrincipal removido - CidDoencas foi deletado

        if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
