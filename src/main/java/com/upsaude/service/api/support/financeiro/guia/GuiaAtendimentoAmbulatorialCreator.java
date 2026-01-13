package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.GuiaAtendimentoAmbulatorialMapper;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialCreator {

    private final GuiaAtendimentoAmbulatorialRepository repository;
    private final GuiaAtendimentoAmbulatorialMapper mapper;
    private final GuiaAtendimentoAmbulatorialValidationService validationService;
    private final GuiaAtendimentoAmbulatorialRelacionamentosHandler relacionamentosHandler;
    private final GuiaAtendimentoAmbulatorialDomainService domainService;

    public GuiaAtendimentoAmbulatorial criar(GuiaAtendimentoAmbulatorialRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        GuiaAtendimentoAmbulatorial entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        GuiaAtendimentoAmbulatorial saved = repository.save(Objects.requireNonNull(entity));
        log.info("Guia ambulatorial criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

