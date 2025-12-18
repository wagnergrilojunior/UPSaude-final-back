package com.upsaude.service.support.equipamentos;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.sistema.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosRelacionamentosHandler {

    public void resolver(Equipamentos entity, EquipamentosRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}
