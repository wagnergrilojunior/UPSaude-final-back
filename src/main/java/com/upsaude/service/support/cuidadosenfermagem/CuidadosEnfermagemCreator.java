package com.upsaude.service.support.cuidadosenfermagem;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.enfermagem.CuidadosEnfermagemMapper;
import com.upsaude.repository.enfermagem.CuidadosEnfermagemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemCreator {

    private final CuidadosEnfermagemRepository repository;
    private final CuidadosEnfermagemMapper mapper;
    private final CuidadosEnfermagemValidationService validationService;
    private final CuidadosEnfermagemRelacionamentosHandler relacionamentosHandler;

    public CuidadosEnfermagem criar(CuidadosEnfermagemRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        CuidadosEnfermagem entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        CuidadosEnfermagem saved = repository.save(Objects.requireNonNull(entity));
        log.info("Cuidados de enfermagem criados com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
