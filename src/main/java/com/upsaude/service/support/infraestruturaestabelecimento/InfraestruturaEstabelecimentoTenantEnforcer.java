package com.upsaude.service.support.infraestruturaestabelecimento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.InfraestruturaEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoTenantEnforcer {

    private final InfraestruturaEstabelecimentoRepository repository;

    public InfraestruturaEstabelecimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à infraestrutura do estabelecimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Infraestrutura do estabelecimento não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Infraestrutura do estabelecimento não encontrada com ID: " + id);
            });
    }

    public InfraestruturaEstabelecimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
