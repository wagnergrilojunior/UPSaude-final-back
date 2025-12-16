package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.mapper.VisitasDomiciliaresMapper;
import com.upsaude.repository.VisitasDomiciliaresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresUpdater {

    private final VisitasDomiciliaresRepository repository;
    private final VisitasDomiciliaresMapper mapper;
    private final VisitasDomiciliaresTenantEnforcer tenantEnforcer;
    private final VisitasDomiciliaresValidationService validationService;
    private final VisitasDomiciliaresRelacionamentosHandler relacionamentosHandler;

    public VisitasDomiciliares atualizar(UUID id, VisitasDomiciliaresRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        VisitasDomiciliares entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        VisitasDomiciliares saved = repository.save(Objects.requireNonNull(entity));
        log.info("Visita domiciliar atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

