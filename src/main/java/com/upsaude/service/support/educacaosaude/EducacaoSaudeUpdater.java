package com.upsaude.service.support.educacaosaude;

import com.upsaude.api.request.educacao.EducacaoSaudeRequest;
import com.upsaude.entity.educacao.EducacaoSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.EducacaoSaudeMapper;
import com.upsaude.repository.saude_publica.educacao.EducacaoSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducacaoSaudeUpdater {

    private final EducacaoSaudeRepository repository;
    private final EducacaoSaudeMapper mapper;
    private final EducacaoSaudeTenantEnforcer tenantEnforcer;
    private final EducacaoSaudeValidationService validationService;
    private final EducacaoSaudeRelacionamentosHandler relacionamentosHandler;
    private final EducacaoSaudeDomainService domainService;

    public EducacaoSaude atualizar(UUID id, EducacaoSaudeRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EducacaoSaude entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EducacaoSaude saved = repository.save(Objects.requireNonNull(entity));
        log.info("Educação em saúde atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
