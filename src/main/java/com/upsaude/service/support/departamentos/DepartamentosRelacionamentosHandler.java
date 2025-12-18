package com.upsaude.service.support.departamentos;

import com.upsaude.api.request.departamento.DepartamentosRequest;
import com.upsaude.entity.departamento.Departamentos;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartamentosRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public Departamentos processarEstabelecimento(DepartamentosRequest request, Departamentos entity, UUID tenantId) {
        if (request.getEstabelecimento() == null) {
            entity.setEstabelecimento(null);
            return entity;
        }

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);
        return entity;
    }
}

