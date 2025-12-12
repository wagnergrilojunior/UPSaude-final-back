package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.AcaoPromocaoPrevencaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoTenantEnforcer {

    private final AcaoPromocaoPrevencaoRepository repository;

    public AcaoPromocaoPrevencao validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à ação promoção/prevenção. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Ação promoção/prevenção não encontrada. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Ação de promoção e prevenção não encontrada com ID: " + id);
                });
    }

    public AcaoPromocaoPrevencao validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo à ação promoção/prevenção. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Ação promoção/prevenção não encontrada (completo). ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Ação de promoção e prevenção não encontrada com ID: " + id);
                });
    }
}
