package com.upsaude.service.support.atividadeprofissional;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.AtividadeProfissionalMapper;
import com.upsaude.repository.profissional.AtividadeProfissionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeProfissionalCreator {

    private final AtividadeProfissionalRepository repository;
    private final AtividadeProfissionalMapper mapper;
    private final AtividadeProfissionalValidationService validationService;
    private final AtividadeProfissionalRelacionamentosHandler relacionamentosHandler;

    public AtividadeProfissional criar(AtividadeProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencia(request);

        AtividadeProfissional entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        AtividadeProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Atividade profissional criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

