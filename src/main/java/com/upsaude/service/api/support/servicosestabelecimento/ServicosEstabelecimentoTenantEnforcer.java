package com.upsaude.service.api.support.servicosestabelecimento;

import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.ServicosEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoTenantEnforcer {

    private final ServicosEstabelecimentoRepository repository;

    public ServicosEstabelecimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao serviço do estabelecimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Serviço do estabelecimento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Serviço do estabelecimento não encontrado com ID: " + id);
            });
    }

    public ServicosEstabelecimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

