package com.upsaude.service.support.equipesaude;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.EquipeSaudeMapper;
import com.upsaude.repository.EquipeSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeUpdater {

    private final EquipeSaudeRepository repository;
    private final EquipeSaudeMapper mapper;
    private final EquipeSaudeTenantEnforcer tenantEnforcer;
    private final EquipeSaudeValidationService validationService;
    private final EquipeSaudeRelacionamentosHandler relacionamentosHandler;

    public EquipeSaude atualizar(UUID id, EquipeSaudeRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        EquipeSaude existente = tenantEnforcer.validarAcesso(id, tenantId);

        validationService.validarUnicidadeParaAtualizacao(id, request, repository, tenantId);

        mapper.updateFromRequest(request, existente);
        relacionamentosHandler.processarRelacionamentos(existente, request, tenantId, Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar equipe"));

        EquipeSaude salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Equipe de saúde atualizada com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
