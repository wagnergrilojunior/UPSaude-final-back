package com.upsaude.service.support.departamentos;

import com.upsaude.api.request.departamento.DepartamentosRequest;
import com.upsaude.entity.departamento.Departamentos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.DepartamentosMapper;
import com.upsaude.repository.estabelecimento.departamento.DepartamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosCreator {

    private final DepartamentosRepository repository;
    private final DepartamentosMapper mapper;
    private final DepartamentosValidationService validationService;
    private final DepartamentosRelacionamentosHandler relacionamentosHandler;

    public Departamentos criar(DepartamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Departamentos entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar departamento"));

        relacionamentosHandler.processarEstabelecimento(request, entity, tenantId);

        Departamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Departamento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

