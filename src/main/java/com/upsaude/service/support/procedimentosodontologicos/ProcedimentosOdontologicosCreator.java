package com.upsaude.service.support.procedimentosodontologicos;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ProcedimentosOdontologicosMapper;
import com.upsaude.repository.ProcedimentosOdontologicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentosOdontologicosCreator {

    private final ProcedimentosOdontologicosRepository repository;
    private final ProcedimentosOdontologicosMapper mapper;
    private final ProcedimentosOdontologicosValidationService validationService;

    public ProcedimentosOdontologicos criar(ProcedimentosOdontologicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        ProcedimentosOdontologicos entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        ProcedimentosOdontologicos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Procedimento odontológico criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

