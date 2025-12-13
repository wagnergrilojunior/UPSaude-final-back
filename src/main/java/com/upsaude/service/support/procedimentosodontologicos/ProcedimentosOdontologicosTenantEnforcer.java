package com.upsaude.service.support.procedimentosodontologicos;

import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ProcedimentosOdontologicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentosOdontologicosTenantEnforcer {

    private final ProcedimentosOdontologicosRepository repository;

    public ProcedimentosOdontologicos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao procedimento odontológico. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Procedimento odontológico não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Procedimento odontológico não encontrado com ID: " + id);
            });
    }

    public ProcedimentosOdontologicos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

