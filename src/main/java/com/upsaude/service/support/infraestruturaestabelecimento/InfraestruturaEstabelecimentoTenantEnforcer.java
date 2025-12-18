package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.InfraestruturaEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoTenantEnforcer {

    private final InfraestruturaEstabelecimentoRepository repository;

    public InfraestruturaEstabelecimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à infraestrutura. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Infraestrutura não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Infraestrutura não encontrada com ID: " + id);
            });
    }

    public InfraestruturaEstabelecimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
