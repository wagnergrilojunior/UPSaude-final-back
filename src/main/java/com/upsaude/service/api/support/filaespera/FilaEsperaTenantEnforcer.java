package com.upsaude.service.api.support.filaespera;

import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.FilaEsperaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilaEsperaTenantEnforcer {

    private final FilaEsperaRepository repository;

    public FilaEspera validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à fila de espera. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Item da fila de espera não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Item da fila de espera não encontrado com ID: " + id);
            });
    }

    public FilaEspera validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
