package com.upsaude.service.support.tratamentosodontologicos;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.odontologia.TratamentosOdontologicosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosTenantEnforcer {

    private final TratamentosOdontologicosRepository repository;

    public TratamentosOdontologicos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao tratamento odontológico. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Tratamento odontológico não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Tratamento odontológico não encontrado com ID: " + id);
            });
    }

    public TratamentosOdontologicos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
