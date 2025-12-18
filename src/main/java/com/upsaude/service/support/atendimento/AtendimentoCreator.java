package com.upsaude.service.support.atendimento;

import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.AtendimentoMapper;
import com.upsaude.repository.atendimento.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoCreator {

    private final AtendimentoRepository repository;
    private final AtendimentoMapper mapper;
    private final AtendimentoValidationService validationService;
    private final AtendimentoRelacionamentosHandler relacionamentosHandler;
    private final AtendimentoDomainService domainService;

    public Atendimento criar(AtendimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Atendimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Atendimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Atendimento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
