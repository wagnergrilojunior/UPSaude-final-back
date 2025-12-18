package com.upsaude.service.support.controleponto;

import com.upsaude.api.request.equipe.ControlePontoRequest;
import com.upsaude.entity.equipe.ControlePonto;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ControlePontoMapper;
import com.upsaude.repository.profissional.equipe.ControlePontoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoCreator {

    private final ControlePontoRepository repository;
    private final ControlePontoMapper mapper;
    private final ControlePontoValidationService validationService;
    private final ControlePontoRelacionamentosHandler relacionamentosHandler;
    private final ControlePontoDomainService domainService;

    public ControlePonto criar(ControlePontoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ControlePonto entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ControlePonto saved = repository.save(Objects.requireNonNull(entity));
        log.info("Registro de ponto criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
