package com.upsaude.service.support.vacinacoes;

import com.upsaude.api.request.vacina.VacinacoesRequest;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.vacina.Vacinacoes;
import com.upsaude.mapper.VacinacoesMapper;
import com.upsaude.repository.saude_publica.vacina.VacinacoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinacoesCreator {

    private final VacinacoesRepository repository;
    private final VacinacoesMapper mapper;
    private final VacinacoesValidationService validationService;
    private final VacinacoesRelacionamentosHandler relacionamentosHandler;

    public Vacinacoes criar(VacinacoesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Vacinacoes entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Vacinacoes saved = repository.save(Objects.requireNonNull(entity));
        log.info("Vacinação criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

