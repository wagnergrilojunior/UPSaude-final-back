package com.upsaude.service.api.support.estabelecimentos;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosUpdater {

    private final EstabelecimentosRepository repository;
    private final EstabelecimentosMapper mapper;
    private final EstabelecimentosTenantEnforcer tenantEnforcer;
    private final EstabelecimentosValidationService validationService;
    private final EstabelecimentosRelacionamentosHandler relacionamentosHandler;

    public Estabelecimentos atualizar(UUID id, EstabelecimentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Estabelecimentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar estabelecimento"));

        relacionamentosHandler.processarEnderecoPrincipal(request, entity, tenantId, tenant);
        relacionamentosHandler.processarResponsaveis(request, entity, tenantId);

        Estabelecimentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Estabelecimento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

