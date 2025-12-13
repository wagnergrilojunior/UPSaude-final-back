package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.DispensacoesMedicamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosTenantEnforcer {

    private final DispensacoesMedicamentosRepository repository;

    public DispensacoesMedicamentos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à dispensação de medicamento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Dispensação de medicamento não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Dispensação de medicamento não encontrada com ID: " + id);
            });
    }

    public DispensacoesMedicamentos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
