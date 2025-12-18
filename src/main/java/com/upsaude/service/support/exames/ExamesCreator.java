package com.upsaude.service.support.exames;

import com.upsaude.api.request.exame.ExamesRequest;
import com.upsaude.entity.exame.Exames;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ExamesMapper;
import com.upsaude.repository.exame.ExamesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesCreator {

    private final ExamesRepository repository;
    private final ExamesMapper mapper;
    private final ExamesValidationService validationService;
    private final ExamesRelacionamentosHandler relacionamentosHandler;

    public Exames criar(ExamesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistenciaDatas(request);

        Exames entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        Exames saved = repository.save(Objects.requireNonNull(entity));
        log.info("Exame criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

