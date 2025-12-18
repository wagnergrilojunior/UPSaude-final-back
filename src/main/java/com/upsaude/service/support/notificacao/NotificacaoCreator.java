package com.upsaude.service.support.notificacao;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.sistema.notificacao.NotificacaoMapper;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoCreator {

    private final NotificacaoRepository repository;
    private final NotificacaoMapper mapper;
    private final NotificacaoValidationService validationService;
    private final NotificacaoRelacionamentosHandler relacionamentosHandler;

    public Notificacao criar(NotificacaoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Notificacao entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Notificacao saved = repository.save(Objects.requireNonNull(entity));
        log.info("Notificação criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
