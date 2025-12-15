package com.upsaude.service.support.checkinatendimento;

import com.upsaude.api.request.CheckInAtendimentoRequest;
import com.upsaude.entity.CheckInAtendimento;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.CheckInAtendimentoMapper;
import com.upsaude.repository.CheckInAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInAtendimentoUpdater {

    private final CheckInAtendimentoRepository repository;
    private final CheckInAtendimentoTenantEnforcer tenantEnforcer;
    private final CheckInAtendimentoValidationService validationService;
    private final CheckInAtendimentoRelacionamentosHandler relacionamentosHandler;
    private final CheckInAtendimentoDomainService domainService;
    private final CheckInAtendimentoMapper mapper;

    public CheckInAtendimento atualizar(UUID id, CheckInAtendimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        CheckInAtendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        CheckInAtendimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Check-in atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
