package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.Farmacia;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.farmacia.DispensacaoRepository;
import com.upsaude.repository.farmacia.FarmaciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacaoTenantEnforcer {

    private final DispensacaoRepository dispensacaoRepository;
    private final FarmaciaRepository farmaciaRepository;

    public Dispensacao validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à dispensação ID: {} para tenant: {}", id, tenantId);

        return dispensacaoRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Dispensação não encontrada com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Dispensação não encontrada com ID: " + id);
                });
    }

    public Farmacia validarFarmacia(UUID farmaciaId, UUID tenantId) {
        log.debug("Validando acesso à farmácia ID: {} para tenant: {}", farmaciaId, tenantId);

        return farmaciaRepository.findByIdAndTenant(farmaciaId, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada com ID: {} para tenant: {}", farmaciaId, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + farmaciaId);
                });
    }
}
