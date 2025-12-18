package com.upsaude.service.support.templatenotificacao;

import com.upsaude.api.request.notificacao.TemplateNotificacaoRequest;
import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.TemplateNotificacaoMapper;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateNotificacaoCreator {

    private final TemplateNotificacaoRepository repository;
    private final TemplateNotificacaoMapper mapper;
    private final TemplateNotificacaoValidationService validationService;
    private final TemplateNotificacaoRelacionamentosHandler relacionamentosHandler;

    public TemplateNotificacao criar(TemplateNotificacaoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        TemplateNotificacao entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        TemplateNotificacao saved = repository.save(Objects.requireNonNull(entity));
        log.info("TemplateNotificacao criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

