package com.upsaude.service.support.historicohabilitacaoprofissional;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.HistoricoHabilitacaoProfissionalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalTenantEnforcer {

    private final HistoricoHabilitacaoProfissionalRepository repository;

    public HistoricoHabilitacaoProfissional validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao histórico de habilitação profissional. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Histórico de habilitação profissional não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Histórico de habilitação profissional não encontrado com ID: " + id);
            });
    }

    public HistoricoHabilitacaoProfissional validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
