package com.upsaude.service.support.responsavellegal;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponsavelLegalRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(ResponsavelLegal entity, ResponsavelLegalRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        if (Boolean.FALSE.equals(paciente.getActive())) {
            throw new BadRequestException("Não é possível vincular responsável legal a um paciente inativo. ID do paciente: " + paciente.getId());
        }
        entity.setPaciente(paciente);

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
        } else {
            entity.setEstabelecimento(null);
        }
    }
}

