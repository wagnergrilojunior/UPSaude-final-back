package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ProcedimentoCirurgicoMapper;
import com.upsaude.repository.ProcedimentoCirurgicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentoCirurgicoUpdater {

    private final ProcedimentoCirurgicoRepository repository;
    private final ProcedimentoCirurgicoMapper mapper;
    private final ProcedimentoCirurgicoTenantEnforcer tenantEnforcer;
    private final ProcedimentoCirurgicoValidationService validationService;
    private final ProcedimentoCirurgicoDomainService domainService;
    private final ProcedimentoCirurgicoRelacionamentosHandler relacionamentosHandler;

    public ProcedimentoCirurgico atualizar(UUID id, ProcedimentoCirurgicoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ProcedimentoCirurgico entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);
        domainService.calcularValorTotalSeAplicavel(entity);

        ProcedimentoCirurgico saved = repository.save(Objects.requireNonNull(entity));
        log.info("Procedimento cirúrgico atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

