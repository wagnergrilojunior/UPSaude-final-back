package com.upsaude.service.api.support.escalatrabalho;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.equipe.EscalaTrabalhoRequest;
import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.profissional.equipe.EscalaTrabalhoMapper;
import com.upsaude.repository.profissional.equipe.EscalaTrabalhoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalaTrabalhoUpdater {

    private final EscalaTrabalhoRepository repository;
    private final EscalaTrabalhoMapper mapper;
    private final EscalaTrabalhoTenantEnforcer tenantEnforcer;
    private final EscalaTrabalhoValidationService validationService;
    private final EscalaTrabalhoRelacionamentosHandler relacionamentosHandler;

    public EscalaTrabalho atualizar(UUID id, EscalaTrabalhoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EscalaTrabalho entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EscalaTrabalho saved = repository.save(Objects.requireNonNull(entity));
        log.info("Escala de trabalho atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
