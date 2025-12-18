package com.upsaude.service.support.receitasmedicas;

import com.upsaude.entity.medicacao.ReceitasMedicas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.medicacao.ReceitasMedicasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitasMedicasTenantEnforcer {

    private final ReceitasMedicasRepository repository;

    public ReceitasMedicas validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à receita médica. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Receita médica não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Receita médica não encontrada com ID: " + id);
            });
    }

    public ReceitasMedicas validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

