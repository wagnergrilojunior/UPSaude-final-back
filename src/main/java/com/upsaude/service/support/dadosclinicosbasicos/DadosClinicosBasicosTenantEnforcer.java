package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosTenantEnforcer {

    private final DadosClinicosBasicosRepository repository;

    public DadosClinicosBasicos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso aos dados clínicos básicos. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Dados clínicos básicos não encontrados. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id);
            });
    }

    public DadosClinicosBasicos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

