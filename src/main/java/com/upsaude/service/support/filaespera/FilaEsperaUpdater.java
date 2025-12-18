package com.upsaude.service.support.filaespera;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.FilaEsperaMapper;
import com.upsaude.repository.agendamento.FilaEsperaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilaEsperaUpdater {

    private final FilaEsperaRepository repository;
    private final FilaEsperaMapper mapper;
    private final FilaEsperaTenantEnforcer tenantEnforcer;
    private final FilaEsperaValidationService validationService;
    private final FilaEsperaRelacionamentosHandler relacionamentosHandler;

    public FilaEspera atualizar(UUID id, FilaEsperaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencias(request);

        FilaEspera entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        FilaEspera saved = repository.save(Objects.requireNonNull(entity));
        log.info("Item da fila de espera atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

