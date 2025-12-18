package com.upsaude.service.support.catalogoexames;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.exame.CatalogoExamesRequest;
import com.upsaude.entity.clinica.exame.CatalogoExames;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.clinica.exame.CatalogoExamesMapper;
import com.upsaude.repository.clinica.exame.CatalogoExamesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoExamesUpdater {

    private final CatalogoExamesRepository repository;
    private final CatalogoExamesMapper mapper;
    private final CatalogoExamesTenantEnforcer tenantEnforcer;
    private final CatalogoExamesValidationService validationService;

    public CatalogoExames atualizar(UUID id, CatalogoExamesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        CatalogoExames existente = tenantEnforcer.validarAcesso(id, tenantId);

        validationService.validarUnicidadeParaAtualizacao(id, request, repository, tenantId);

        mapper.updateFromRequest(request, existente);
        existente.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar exame no catálogo"));

        CatalogoExames salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Exame atualizado no catálogo com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
