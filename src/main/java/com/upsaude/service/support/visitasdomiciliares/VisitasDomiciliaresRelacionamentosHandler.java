package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

