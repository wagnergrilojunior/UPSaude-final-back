package com.upsaude.service.support.consultaprenatal;

import com.upsaude.api.request.atendimento.ConsultaPreNatalRequest;
import com.upsaude.entity.atendimento.ConsultaPreNatal;
import com.upsaude.entity.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ConsultaPreNatalMapper;
import com.upsaude.repository.atendimento.ConsultaPreNatalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPreNatalCreator {

    private final ConsultaPreNatalRepository repository;
    private final ConsultaPreNatalMapper mapper;
    private final ConsultaPreNatalValidationService validationService;
    private final ConsultaPreNatalRelacionamentosHandler relacionamentosHandler;
    private final ConsultaPreNatalDomainService domainService;

    public ConsultaPreNatal criar(ConsultaPreNatalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ConsultaPreNatal entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        ConsultaPreNatal saved = repository.save(Objects.requireNonNull(entity));

        PreNatal preNatal = saved.getPreNatal();
        domainService.recalcularNumeroConsultas(preNatal, tenantId);

        log.info("Consulta pré-natal criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

