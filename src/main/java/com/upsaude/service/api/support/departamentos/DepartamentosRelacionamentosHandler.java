package com.upsaude.service.api.support.departamentos;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.departamento.DepartamentosRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.departamento.Departamentos;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosTenantEnforcer;

import lombok.RequiredArgsConstructor;

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
