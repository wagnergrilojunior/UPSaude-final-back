package com.upsaude.service.support.equipesaude;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EstabelecimentosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipeSaudeRelacionamentosHandler {

    private final EstabelecimentosRepository estabelecimentosRepository;

    public EquipeSaude processarRelacionamentos(EquipeSaude equipe, EquipeSaudeRequest request, UUID tenantId, Tenant tenant) {
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimento())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));

        if (estabelecimento.getTenant() == null || estabelecimento.getTenant().getId() == null || !estabelecimento.getTenant().getId().equals(tenantId)) {
            throw new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento());
        }

        equipe.setEstabelecimento(estabelecimento);
        equipe.setTenant(tenant);
        return equipe;
    }
}
