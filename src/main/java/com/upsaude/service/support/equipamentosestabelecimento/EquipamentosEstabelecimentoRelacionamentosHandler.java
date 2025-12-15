package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.equipamentos.EquipamentosTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final EquipamentosTenantEnforcer equipamentosTenantEnforcer;

    public void resolver(EquipamentosEstabelecimento entity, EquipamentosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getEstabelecimento(), "estabelecimento é obrigatório"), tenantId);
        entity.setEstabelecimento(estabelecimento);

        Equipamentos equipamento = equipamentosTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getEquipamento(), "equipamento é obrigatório"), tenantId);
        entity.setEquipamento(equipamento);
    }
}
