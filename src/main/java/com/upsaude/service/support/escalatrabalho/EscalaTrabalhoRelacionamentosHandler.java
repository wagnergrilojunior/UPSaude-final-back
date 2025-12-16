package com.upsaude.service.support.escalatrabalho;

import com.upsaude.api.request.EscalaTrabalhoRequest;
import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EscalaTrabalhoRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;

    public void resolver(EscalaTrabalho entity, EscalaTrabalhoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getProfissional(), "profissional é obrigatório"), tenantId);
        entity.setProfissional(profissional);

        if (request.getMedico() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
            entity.setMedico(medico);
        } else {
            entity.setMedico(null);
        }

        if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
