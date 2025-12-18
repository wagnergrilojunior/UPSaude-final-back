package com.upsaude.service.support.prontuarios;

import com.upsaude.api.request.prontuario.ProntuariosRequest;
import com.upsaude.entity.prontuario.Prontuarios;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ProntuariosMapper;
import com.upsaude.repository.prontuario.ProntuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuariosCreator {

    private final ProntuariosRepository repository;
    private final ProntuariosMapper mapper;
    private final ProntuariosValidationService validationService;
    private final ProntuariosRelacionamentosHandler relacionamentosHandler;

    public Prontuarios criar(ProntuariosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Prontuarios entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Prontuarios saved = repository.save(Objects.requireNonNull(entity));
        log.info("Prontuário criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

