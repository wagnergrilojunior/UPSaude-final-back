package com.upsaude.service.support.dadossociodemograficos;

import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosSociodemograficosTenantEnforcer {

    private final DadosSociodemograficosRepository repository;

    public DadosSociodemograficos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso aos dados sociodemográficos. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Dados sociodemográficos não encontrados. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id);
            });
    }

    public DadosSociodemograficos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

