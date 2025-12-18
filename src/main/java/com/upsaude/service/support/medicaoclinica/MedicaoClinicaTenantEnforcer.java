package com.upsaude.service.support.medicaoclinica;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.profissional.medicao.MedicaoClinica;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.medicao.MedicaoClinicaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicaoClinicaTenantEnforcer {

    private final MedicaoClinicaRepository repository;

    public MedicaoClinica validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à medição clínica. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Medição clínica não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Medição clínica não encontrada com ID: " + id);
            });
    }

    public MedicaoClinica validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
