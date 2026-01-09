package com.upsaude.service.api.support.equipesaude;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.equipe.EquipeSaudeRequest;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
