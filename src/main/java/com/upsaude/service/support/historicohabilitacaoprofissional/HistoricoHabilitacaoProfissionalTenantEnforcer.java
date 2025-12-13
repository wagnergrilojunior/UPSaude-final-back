package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.HistoricoHabilitacaoProfissionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalTenantEnforcer {

    private final HistoricoHabilitacaoProfissionalRepository repository;

    public HistoricoHabilitacaoProfissional validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao histórico de habilitação. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Registro não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Histórico de habilitação não encontrado com ID: " + id);
            });
    }

    public HistoricoHabilitacaoProfissional validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
