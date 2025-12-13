package com.upsaude.service.support.consultapuericultura;

import com.upsaude.api.request.ConsultaPuericulturaRequest;
import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ConsultaPuericulturaMapper;
import com.upsaude.repository.ConsultaPuericulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaCreator {

    private final ConsultaPuericulturaRepository repository;
    private final ConsultaPuericulturaMapper mapper;
    private final ConsultaPuericulturaValidationService validationService;
    private final ConsultaPuericulturaRelacionamentosHandler relacionamentosHandler;

    public ConsultaPuericultura criar(ConsultaPuericulturaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ConsultaPuericultura entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ConsultaPuericultura saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consulta de puericultura criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

