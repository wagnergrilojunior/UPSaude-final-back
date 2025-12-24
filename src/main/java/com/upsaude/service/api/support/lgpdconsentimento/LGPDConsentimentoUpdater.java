package com.upsaude.service.api.support.lgpdconsentimento;

import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.sistema.lgpd.LGPDConsentimentoMapper;
import com.upsaude.repository.sistema.lgpd.LGPDConsentimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LGPDConsentimentoUpdater {

    private final LGPDConsentimentoRepository repository;
    private final LGPDConsentimentoTenantEnforcer tenantEnforcer;
    private final LGPDConsentimentoValidationService validationService;
    private final LGPDConsentimentoRelacionamentosHandler relacionamentosHandler;
    private final LGPDConsentimentoDomainService domainService;
    private final LGPDConsentimentoMapper mapper;

    public LGPDConsentimento atualizar(UUID id, LGPDConsentimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        LGPDConsentimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getPaciente() != null && entity.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
            repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
                .ifPresent(d -> {
                    if (!d.getId().equals(id)) {
                        throw new ConflictException("Consentimento LGPD já existe para este paciente");
                    }
                });
        }

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        LGPDConsentimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consentimento LGPD atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
