package com.upsaude.service.api.support.templatenotificacao;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.sistema.notificacao.TemplateNotificacaoMapper;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        log.info("Template de notificação criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
