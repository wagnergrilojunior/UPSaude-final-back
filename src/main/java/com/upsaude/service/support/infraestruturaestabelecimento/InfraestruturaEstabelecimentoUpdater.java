package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.InfraestruturaEstabelecimentoMapper;
import com.upsaude.repository.InfraestruturaEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoUpdater {

    private final InfraestruturaEstabelecimentoRepository repository;
    private final InfraestruturaEstabelecimentoTenantEnforcer tenantEnforcer;
    private final InfraestruturaEstabelecimentoValidationService validationService;
    private final InfraestruturaEstabelecimentoRelacionamentosHandler relacionamentosHandler;
    private final InfraestruturaEstabelecimentoDomainService domainService;
    private final InfraestruturaEstabelecimentoMapper mapper;

    public InfraestruturaEstabelecimento atualizar(UUID id, InfraestruturaEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        InfraestruturaEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        InfraestruturaEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Infraestrutura atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
