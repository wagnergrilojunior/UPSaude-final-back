package com.upsaude.service.support.equipamentos;

import com.upsaude.api.request.EquipamentosRequest;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.FabricantesEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosRelacionamentosHandler {

    private final FabricantesEquipamentoRepository fabricantesEquipamentoRepository;

    public void resolver(Equipamentos entity, EquipamentosRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getFabricante() != null) {
            UUID fabricanteId = request.getFabricante();
            FabricantesEquipamento fabricante = fabricantesEquipamentoRepository.findById(fabricanteId)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + fabricanteId));
            entity.setFabricante(fabricante);
        } else {
            entity.setFabricante(null);
        }

        // Equipamento é catálogo por tenant; não força estabelecimento.
        entity.setEstabelecimento(null);
    }
}
