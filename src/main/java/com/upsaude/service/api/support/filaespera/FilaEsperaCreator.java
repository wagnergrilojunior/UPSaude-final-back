package com.upsaude.service.api.support.filaespera;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.agendamento.FilaEsperaMapper;
import com.upsaude.repository.agendamento.FilaEsperaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilaEsperaCreator {

    private final FilaEsperaRepository repository;
    private final FilaEsperaMapper mapper;
    private final FilaEsperaValidationService validationService;
    private final FilaEsperaRelacionamentosHandler relacionamentosHandler;

    public FilaEspera criar(FilaEsperaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencias(request);

        FilaEspera entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        FilaEspera saved = repository.save(Objects.requireNonNull(entity));
        log.info("Item da fila de espera criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
