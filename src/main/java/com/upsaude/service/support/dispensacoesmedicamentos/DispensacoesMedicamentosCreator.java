package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.DispensacoesMedicamentosMapper;
import com.upsaude.repository.DispensacoesMedicamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosCreator {

    private final DispensacoesMedicamentosRepository repository;
    private final DispensacoesMedicamentosMapper mapper;
    private final DispensacoesMedicamentosValidationService validationService;
    private final DispensacoesMedicamentosRelacionamentosHandler relacionamentosHandler;
    private final DispensacoesMedicamentosDomainService domainService;

    public DispensacoesMedicamentos criar(DispensacoesMedicamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        DispensacoesMedicamentos entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        DispensacoesMedicamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Dispensação de medicamento criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
