package com.upsaude.service.support.vacinacoes;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.VacinacoesRequest;
import com.upsaude.entity.saude_publica.vacina.Vacinacoes;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.saude_publica.vacina.VacinacoesMapper;
import com.upsaude.repository.saude_publica.vacina.VacinacoesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinacoesUpdater {

    private final VacinacoesRepository repository;
    private final VacinacoesMapper mapper;
    private final VacinacoesTenantEnforcer tenantEnforcer;
    private final VacinacoesValidationService validationService;
    private final VacinacoesRelacionamentosHandler relacionamentosHandler;

    public Vacinacoes atualizar(UUID id, VacinacoesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Vacinacoes entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Vacinacoes saved = repository.save(Objects.requireNonNull(entity));
        log.info("Vacinação atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

