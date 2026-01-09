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
public class LGPDConsentimentoCreator {

    private final LGPDConsentimentoRepository repository;
    private final LGPDConsentimentoMapper mapper;
    private final LGPDConsentimentoValidationService validationService;
    private final LGPDConsentimentoRelacionamentosHandler relacionamentosHandler;
    private final LGPDConsentimentoDomainService domainService;

    public LGPDConsentimento criar(LGPDConsentimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
            .ifPresent(d -> {
                throw new ConflictException("Consentimento LGPD já existe para este paciente");
            });

        LGPDConsentimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        LGPDConsentimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consentimento LGPD criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
