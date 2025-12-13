package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.cirurgia.CirurgiaTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcedimentoCirurgicoRelacionamentosHandler {

    private final CirurgiaTenantEnforcer cirurgiaTenantEnforcer;

    public ProcedimentoCirurgico processarRelacionamentos(ProcedimentoCirurgicoRequest request,
                                                         ProcedimentoCirurgico entity,
                                                         UUID tenantId,
                                                         Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        Cirurgia cirurgia = cirurgiaTenantEnforcer.validarAcesso(request.getCirurgia(), tenantId);
        entity.setCirurgia(cirurgia);

        if (cirurgia.getEstabelecimento() != null) {
            entity.setEstabelecimento(cirurgia.getEstabelecimento());
        }

        return entity;
    }
}

