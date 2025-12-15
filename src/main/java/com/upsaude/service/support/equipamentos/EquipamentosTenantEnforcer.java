package com.upsaude.service.support.equipamentos;

import com.upsaude.entity.Equipamentos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EquipamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosTenantEnforcer {

    private final EquipamentosRepository repository;

    public Equipamentos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao equipamento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Equipamento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Equipamento não encontrado com ID: " + id);
            });
    }

    public Equipamentos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
