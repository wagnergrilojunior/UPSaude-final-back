package com.upsaude.service.support.visitasdomiciliares;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.visita.VisitasDomiciliaresRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.saude_publica.visita.VisitasDomiciliares;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(VisitasDomiciliares entity, VisitasDomiciliaresRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
        entity.setProfissional(profissional);

        if (request.getEquipeSaude() != null) {
            EquipeSaude equipeSaude = equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId);
            entity.setEquipeSaude(equipeSaude);
        } else {
            entity.setEquipeSaude(null);
        }

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
            return;
        }

        if (profissional.getEstabelecimento() != null) {
            entity.setEstabelecimento(profissional.getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}

