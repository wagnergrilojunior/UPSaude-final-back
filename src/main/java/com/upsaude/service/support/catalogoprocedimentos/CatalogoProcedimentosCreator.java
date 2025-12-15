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
public class CatalogoProcedimentosCreator {

    private final CatalogoProcedimentosRepository repository;
    private final CatalogoProcedimentosMapper mapper;
    private final CatalogoProcedimentosValidationService validationService;

    public CatalogoProcedimentos criar(CatalogoProcedimentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, repository, tenantId);

        CatalogoProcedimentos procedimento = mapper.fromRequest(request);
        procedimento.setActive(true);
        procedimento.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar procedimento no catálogo"));

        CatalogoProcedimentos salvo = repository.save(Objects.requireNonNull(procedimento));
        log.info("Procedimento criado no catálogo com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}

