package com.upsaude.service.support.checkinatendimento;

import com.upsaude.api.request.atendimento.CheckInAtendimentoRequest;
import com.upsaude.entity.atendimento.CheckInAtendimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.CheckInAtendimentoMapper;
import com.upsaude.repository.atendimento.CheckInAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInAtendimentoCreator {

    private final CheckInAtendimentoRepository repository;
    private final CheckInAtendimentoMapper mapper;
    private final CheckInAtendimentoValidationService validationService;
    private final CheckInAtendimentoRelacionamentosHandler relacionamentosHandler;
    private final CheckInAtendimentoDomainService domainService;

    public CheckInAtendimento criar(CheckInAtendimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        CheckInAtendimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        CheckInAtendimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Check-in criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
