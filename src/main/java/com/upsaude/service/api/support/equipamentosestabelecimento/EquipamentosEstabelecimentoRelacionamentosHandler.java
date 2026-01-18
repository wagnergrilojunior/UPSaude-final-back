package com.upsaude.service.api.support.equipamentosestabelecimento;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final EquipamentosRepository equipamentosRepository;

    public void resolver(EquipamentosEstabelecimento entity, EquipamentosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
        }

        if (request.getEquipamento() != null) {
            Equipamentos equipamento = equipamentosRepository.findByIdAndTenant(request.getEquipamento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Equipamento não encontrado com ID: " + request.getEquipamento()));
            entity.setEquipamento(equipamento);
        }
    }
}
