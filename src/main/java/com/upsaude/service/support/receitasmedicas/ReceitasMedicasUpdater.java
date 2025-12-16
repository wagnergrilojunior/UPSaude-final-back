package com.upsaude.service.support.receitasmedicas;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ReceitasMedicasMapper;
import com.upsaude.repository.ReceitasMedicasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitasMedicasUpdater {

    private final ReceitasMedicasRepository repository;
    private final ReceitasMedicasMapper mapper;
    private final ReceitasMedicasTenantEnforcer tenantEnforcer;
    private final ReceitasMedicasValidationService validationService;
    private final ReceitasMedicasRelacionamentosHandler relacionamentosHandler;

    public ReceitasMedicas atualizar(UUID id, ReceitasMedicasRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ReceitasMedicas entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ReceitasMedicas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Receita médica atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

