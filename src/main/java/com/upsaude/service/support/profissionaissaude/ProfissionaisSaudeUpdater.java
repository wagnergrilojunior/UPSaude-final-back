package com.upsaude.service.support.profissionaissaude;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeUpdater {

    private final ProfissionaisSaudeValidationService validationService;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;
    private final ProfissionaisSaudeTenantEnforcer tenantEnforcer;
    private final ProfissionaisSaudeOneToOneRelationshipHandler oneToOneHandler;
    private final ProfissionaisSaudeAssociacoesHandler associacoesHandler;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    public ProfissionaisSaude atualizar(UUID id, ProfissionaisSaudeRequest request, UUID tenantId, Tenant tenant) {
        log.debug("Atualizando profissional de saúde. ID: {}", id);

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, profissionaisSaudeRepository, tenantId);
        validationService.sanitizarFlags(request);

        ProfissionaisSaude profissionalExistente = tenantEnforcer.validarAcesso(id, tenantId);

        Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar profissional de saúde");

        profissionaisSaudeMapper.updateFromRequest(request, profissionalExistente);

        oneToOneHandler.processarRelacionamentos(profissionalExistente, request, tenant);
        associacoesHandler.processarEspecialidades(profissionalExistente, request);

        ProfissionaisSaude profissionalAtualizado = profissionaisSaudeRepository.save(Objects.requireNonNull(profissionalExistente));
        log.info("Profissional de saúde atualizado com sucesso. ID: {}, Tenant: {}", profissionalAtualizado.getId(), tenantId);

        return profissionalAtualizado;
    }
}
