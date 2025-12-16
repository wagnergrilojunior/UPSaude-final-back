package com.upsaude.service.support.medicaoclinica;

import com.upsaude.entity.MedicaoClinica;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.MedicaoClinicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
