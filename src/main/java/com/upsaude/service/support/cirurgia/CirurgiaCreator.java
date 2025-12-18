package com.upsaude.service.support.cirurgia;

import com.upsaude.api.request.cirurgia.CirurgiaRequest;
import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.CirurgiaMapper;
import com.upsaude.repository.cirurgia.CirurgiaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CirurgiaCreator {

    private final CirurgiaRepository repository;
    private final CirurgiaMapper mapper;
    private final CirurgiaValidationService validationService;
    private final CirurgiaRelacionamentosHandler relacionamentosHandler;

    public Cirurgia criar(CirurgiaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Cirurgia entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar cirurgia"));

        relacionamentosHandler.processarRelacionamentos(entity, request, tenantId);

        Cirurgia saved = repository.save(Objects.requireNonNull(entity));
        log.info("Cirurgia criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

