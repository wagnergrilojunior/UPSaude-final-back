package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeCreator {

    private final ProfissionaisSaudeValidationService validationService;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;
    private final ProfissionaisSaudeOneToOneRelationshipHandler oneToOneHandler;
    private final ProfissionaisSaudeAssociacoesHandler associacoesHandler;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    public ProfissionaisSaude criar(ProfissionaisSaudeRequest request, UUID tenantId, Tenant tenant) {
        log.debug("Criando novo profissional de saúde: {}", request != null ? request.getNomeCompleto() : "null");

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, profissionaisSaudeRepository, tenantId);
        validationService.sanitizarFlags(request);

        ProfissionaisSaude profissional = profissionaisSaudeMapper.fromRequest(request);
        profissional.setActive(true);

        Objects.requireNonNull(tenant, "tenant é obrigatório para criar profissional de saúde");
        profissional.setTenant(tenant);

        oneToOneHandler.processarRelacionamentos(profissional, request, tenant);
        associacoesHandler.processarEspecialidades(profissional, request);

        ProfissionaisSaude profissionalSalvo = profissionaisSaudeRepository.save(Objects.requireNonNull(profissional));
        log.info("Profissional de saúde criado com sucesso. ID: {}, Tenant: {}", profissionalSalvo.getId(), tenantId);

        return profissionalSalvo;
    }
}
