package com.upsaude.service.support.catalogoprocedimentos;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.CatalogoProcedimentosMapper;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosUpdater {

    private final CatalogoProcedimentosRepository repository;
    private final CatalogoProcedimentosMapper mapper;
    private final CatalogoProcedimentosTenantEnforcer tenantEnforcer;
    private final CatalogoProcedimentosValidationService validationService;

    public CatalogoProcedimentos atualizar(UUID id, CatalogoProcedimentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        CatalogoProcedimentos existente = tenantEnforcer.validarAcesso(id, tenantId);
        validationService.validarUnicidadeParaAtualizacao(id, request, repository, tenantId);

        mapper.updateFromRequest(request, existente);
        existente.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar procedimento no catálogo"));

        CatalogoProcedimentos salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Procedimento atualizado no catálogo com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}

