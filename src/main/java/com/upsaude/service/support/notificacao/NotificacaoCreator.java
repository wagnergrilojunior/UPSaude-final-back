package com.upsaude.service.support.notificacao;

import com.upsaude.api.request.NotificacaoRequest;
import com.upsaude.entity.Notificacao;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.NotificacaoMapper;
import com.upsaude.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

