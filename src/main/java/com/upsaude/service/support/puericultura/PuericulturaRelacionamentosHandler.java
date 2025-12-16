package com.upsaude.service.support.puericultura;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PuericulturaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;

    public void resolver(Puericultura entity, PuericulturaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissionalResponsavel() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalResponsavel(), tenantId);
            entity.setProfissionalResponsavel(profissional);
        }

        if (request.getEquipeSaude() != null) {
            EquipeSaude equipe = equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId);
            entity.setEquipeSaude(equipe);
        }

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        // Regra prática: se houver estabelecimento inferível, propagar para BaseEntity.estabelecimento
        Estabelecimentos estabelecimento = null;
        if (entity.getProfissionalResponsavel() != null) {
            estabelecimento = entity.getProfissionalResponsavel().getEstabelecimento();
        }
        if (estabelecimento == null && entity.getEquipeSaude() != null) {
            estabelecimento = entity.getEquipeSaude().getEstabelecimento();
        }
        if (estabelecimento != null) {
            entity.setEstabelecimento(estabelecimento);
        }
    }
}

