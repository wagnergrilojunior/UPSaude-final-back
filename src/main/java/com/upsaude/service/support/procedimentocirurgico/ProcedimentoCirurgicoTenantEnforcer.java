package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ProcedimentoCirurgicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentoCirurgicoTenantEnforcer {

    private final ProcedimentoCirurgicoRepository repository;

    public ProcedimentoCirurgico validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao procedimento cirúrgico. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Procedimento cirúrgico não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Procedimento cirúrgico não encontrado com ID: " + id);
            });
    }

    public ProcedimentoCirurgico validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

