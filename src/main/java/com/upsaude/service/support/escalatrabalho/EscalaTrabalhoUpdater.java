package com.upsaude.service.support.escalatrabalho;

import com.upsaude.api.request.equipe.EscalaTrabalhoRequest;
import com.upsaude.entity.equipe.EscalaTrabalho;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.EscalaTrabalhoMapper;
import com.upsaude.repository.profissional.equipe.EscalaTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalaTrabalhoUpdater {

    private final EscalaTrabalhoRepository repository;
    private final EscalaTrabalhoMapper mapper;
    private final EscalaTrabalhoTenantEnforcer tenantEnforcer;
    private final EscalaTrabalhoValidationService validationService;
    private final EscalaTrabalhoRelacionamentosHandler relacionamentosHandler;
    private final EscalaTrabalhoDomainService domainService;

    public EscalaTrabalho atualizar(UUID id, EscalaTrabalhoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EscalaTrabalho entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EscalaTrabalho saved = repository.save(Objects.requireNonNull(entity));
        log.info("Escala de trabalho atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
