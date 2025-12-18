package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.api.request.visita.VisitasDomiciliaresRequest;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.visita.VisitasDomiciliares;
import com.upsaude.mapper.VisitasDomiciliaresMapper;
import com.upsaude.repository.saude_publica.visita.VisitasDomiciliaresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresCreator {

    private final VisitasDomiciliaresRepository repository;
    private final VisitasDomiciliaresMapper mapper;
    private final VisitasDomiciliaresValidationService validationService;
    private final VisitasDomiciliaresRelacionamentosHandler relacionamentosHandler;

    public VisitasDomiciliares criar(VisitasDomiciliaresRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        VisitasDomiciliares entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        VisitasDomiciliares saved = repository.save(Objects.requireNonNull(entity));
        log.info("Visita domiciliar criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

