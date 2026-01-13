package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialTenantEnforcer {

    private final GuiaAtendimentoAmbulatorialRepository repository;

    public GuiaAtendimentoAmbulatorial validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Guia ambulatorial não encontrada com ID: " + id));
    }

    public GuiaAtendimentoAmbulatorial validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

