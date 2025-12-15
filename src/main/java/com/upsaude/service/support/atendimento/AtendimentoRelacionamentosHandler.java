package com.upsaude.service.support.atendimento;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
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
    private final CidDoencasRepository cidDoencasRepository;

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

        if (request.getCidPrincipal() != null) {
            UUID cidId = Objects.requireNonNull(request.getCidPrincipal(), "cidPrincipal");
            CidDoencas cid = cidDoencasRepository.findById(cidId)
                .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + cidId));
            entity.setCidPrincipal(cid);
        } else {
            entity.setCidPrincipal(null);
        }

        if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
