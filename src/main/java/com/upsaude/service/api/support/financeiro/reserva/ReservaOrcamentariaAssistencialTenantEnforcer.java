package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialTenantEnforcer {

    private final ReservaOrcamentariaAssistencialRepository repository;

    public ReservaOrcamentariaAssistencial validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Reserva orçamentária não encontrada com ID: " + id));
    }

    public ReservaOrcamentariaAssistencial validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

