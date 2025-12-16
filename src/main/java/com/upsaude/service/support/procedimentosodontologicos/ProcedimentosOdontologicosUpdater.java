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
public class ProcedimentosOdontologicosUpdater {

    private final ProcedimentosOdontologicosRepository repository;
    private final ProcedimentosOdontologicosMapper mapper;
    private final ProcedimentosOdontologicosTenantEnforcer tenantEnforcer;
    private final ProcedimentosOdontologicosValidationService validationService;

    public ProcedimentosOdontologicos atualizar(UUID id, ProcedimentosOdontologicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        ProcedimentosOdontologicos entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        ProcedimentosOdontologicos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Procedimento odontológico atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

