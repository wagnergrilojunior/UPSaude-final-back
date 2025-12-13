package com.upsaude.service.support.escalatrabalho;

import com.upsaude.api.request.EscalaTrabalhoRequest;
import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.EscalaTrabalhoMapper;
import com.upsaude.repository.EscalaTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalaTrabalhoCreator {

    private final EscalaTrabalhoRepository repository;
    private final EscalaTrabalhoMapper mapper;
    private final EscalaTrabalhoValidationService validationService;
    private final EscalaTrabalhoRelacionamentosHandler relacionamentosHandler;
    private final EscalaTrabalhoDomainService domainService;

    public EscalaTrabalho criar(EscalaTrabalhoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EscalaTrabalho entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EscalaTrabalho saved = repository.save(Objects.requireNonNull(entity));
        log.info("Escala de trabalho criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
