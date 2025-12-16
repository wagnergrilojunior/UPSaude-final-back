package com.upsaude.service.support.consultas;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ConsultasMapper;
import com.upsaude.repository.ConsultasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasCreator {

    private final ConsultasRepository repository;
    private final ConsultasMapper mapper;
    private final ConsultasValidationService validationService;
    private final ConsultasRelacionamentosHandler relacionamentosHandler;
    private final ConsultasDomainService domainService;

    public Consultas criar(ConsultasRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Consultas entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Consultas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consulta criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
