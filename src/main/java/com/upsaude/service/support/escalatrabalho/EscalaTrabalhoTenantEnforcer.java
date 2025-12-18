package com.upsaude.service.support.escalatrabalho;

import com.upsaude.entity.equipe.EscalaTrabalho;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.EscalaTrabalhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalaTrabalhoTenantEnforcer {

    private final EscalaTrabalhoRepository repository;

    public EscalaTrabalho validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à escala de trabalho. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Escala de trabalho não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Escala de trabalho não encontrada com ID: " + id);
            });
    }

    public EscalaTrabalho validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
