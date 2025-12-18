package com.upsaude.service.support.controleponto;

import com.upsaude.api.request.profissional.equipe.ControlePontoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ControlePontoRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final MedicosRepository medicosRepository;

    public void resolver(ControlePonto entity, ControlePontoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        }

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findByIdAndTenant(request.getMedico(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            entity.setMedico(medico);
        }
    }
}
