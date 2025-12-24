package com.upsaude.service.api.support.permissoes;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.usuario.PermissoesRequest;
import com.upsaude.entity.sistema.usuario.Permissoes;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.sistema.usuario.PermissoesMapper;
import com.upsaude.repository.sistema.usuario.PermissoesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissoesCreator {

    private final PermissoesRepository repository;
    private final PermissoesMapper mapper;
    private final PermissoesValidationService validationService;

    public Permissoes criar(PermissoesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Permissoes entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Permissoes saved = repository.save(Objects.requireNonNull(entity));
        log.info("Permissão criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

