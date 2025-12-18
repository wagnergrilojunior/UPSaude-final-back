package com.upsaude.service.support.equipamentosestabelecimento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoTenantEnforcer {

    private final EquipamentosEstabelecimentoRepository repository;

    public EquipamentosEstabelecimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao equipamento do estabelecimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Equipamento do estabelecimento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Equipamento do estabelecimento não encontrado com ID: " + id);
            });
    }

    public EquipamentosEstabelecimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
