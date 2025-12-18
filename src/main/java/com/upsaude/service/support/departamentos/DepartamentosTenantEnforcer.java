package com.upsaude.service.support.departamentos;

import com.upsaude.entity.departamento.Departamentos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.departamento.DepartamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosTenantEnforcer {

    private final DepartamentosRepository repository;

    public Departamentos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao departamento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Departamento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Departamento não encontrado com ID: " + id);
            });
    }

    public Departamentos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

