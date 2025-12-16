package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.TratamentosOdontologicosMapper;
import com.upsaude.repository.TratamentosOdontologicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosCreator {

    private final TratamentosOdontologicosRepository repository;
    private final TratamentosOdontologicosMapper mapper;
    private final TratamentosOdontologicosValidationService validationService;
    private final TratamentosOdontologicosRelacionamentosHandler relacionamentosHandler;

    public TratamentosOdontologicos criar(TratamentosOdontologicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        TratamentosOdontologicos entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        TratamentosOdontologicos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Tratamento odontológico criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

