package com.upsaude.service.support.tratamentosodontologicos;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.odontologia.TratamentosOdontologicosRequest;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.odontologia.TratamentosOdontologicosMapper;
import com.upsaude.repository.odontologia.TratamentosOdontologicosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
