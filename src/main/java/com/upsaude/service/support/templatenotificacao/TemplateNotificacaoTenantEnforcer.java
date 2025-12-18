package com.upsaude.service.support.templatenotificacao;

import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateNotificacaoTenantEnforcer {

    private final TemplateNotificacaoRepository repository;

    public TemplateNotificacao validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao template de notificação. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Template de notificação não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Template de notificação não encontrado com ID: " + id);
            });
    }

    public TemplateNotificacao validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

