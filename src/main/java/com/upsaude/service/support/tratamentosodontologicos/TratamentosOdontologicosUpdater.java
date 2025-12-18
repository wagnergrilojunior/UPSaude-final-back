package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.api.request.odontologia.TratamentosOdontologicosRequest;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.TratamentosOdontologicosMapper;
import com.upsaude.repository.odontologia.TratamentosOdontologicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosUpdater {

    private final TratamentosOdontologicosRepository repository;
    private final TratamentosOdontologicosMapper mapper;
    private final TratamentosOdontologicosTenantEnforcer tenantEnforcer;
    private final TratamentosOdontologicosValidationService validationService;
    private final TratamentosOdontologicosRelacionamentosHandler relacionamentosHandler;

    public TratamentosOdontologicos atualizar(UUID id, TratamentosOdontologicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        TratamentosOdontologicos entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        TratamentosOdontologicos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Tratamento odontológico atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

