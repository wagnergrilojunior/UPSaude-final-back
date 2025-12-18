package com.upsaude.service.support.prenatal;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.planejamento.PreNatalRequest;
import com.upsaude.entity.saude_publica.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.saude_publica.planejamento.PreNatalMapper;
import com.upsaude.repository.saude_publica.planejamento.PreNatalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreNatalCreator {

    private final PreNatalRepository repository;
    private final PreNatalMapper mapper;
    private final PreNatalValidationService validationService;
    private final PreNatalDomainService domainService;
    private final PreNatalRelacionamentosHandler relacionamentosHandler;

    public PreNatal criar(PreNatalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        PreNatal entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaultsNaCriacao(entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        PreNatal saved = repository.save(Objects.requireNonNull(entity));
        log.info("Pré-natal criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

