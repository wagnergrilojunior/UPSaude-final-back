package com.upsaude.service.support.educacaosaude;

import com.upsaude.entity.educacao.EducacaoSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.educacao.EducacaoSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducacaoSaudeTenantEnforcer {

    private final EducacaoSaudeRepository repository;

    public EducacaoSaude validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à educação em saúde. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Educação em saúde não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Educação em saúde não encontrada com ID: " + id);
            });
    }

    public EducacaoSaude validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
