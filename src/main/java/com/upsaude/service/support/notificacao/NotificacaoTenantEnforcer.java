package com.upsaude.service.support.notificacao;

import com.upsaude.entity.notificacao.Notificacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoTenantEnforcer {

    private final NotificacaoRepository repository;

    public Notificacao validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à notificação. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Notificação não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Notificação não encontrada com ID: " + id);
            });
    }

    public Notificacao validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

