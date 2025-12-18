package com.upsaude.service.support.atendimento;

import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.EspecialidadesMedicasRepository;
import com.upsaude.service.support.convenio.ConvenioTenantEnforcer;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtendimentoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;
    private final ConvenioTenantEnforcer convenioTenantEnforcer;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

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

        if (request.getEspecialidade() != null) {
            UUID especialidadeId = Objects.requireNonNull(request.getEspecialidade(), "especialidade");
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(especialidadeId)
                .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + especialidadeId));
            entity.setEspecialidade(especialidade);
        } else {
            entity.setEspecialidade(null);
        }

        if (request.getEquipeSaude() != null) {
            EquipeSaude equipeSaude = equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId);
            entity.setEquipeSaude(equipeSaude);
        } else {
            entity.setEquipeSaude(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioTenantEnforcer.validarAcesso(request.getConvenio(), tenantId);
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
