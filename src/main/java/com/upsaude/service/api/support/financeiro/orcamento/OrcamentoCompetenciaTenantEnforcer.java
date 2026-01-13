package com.upsaude.service.api.support.financeiro.orcamento;

import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrcamentoCompetenciaTenantEnforcer {

    private final OrcamentoCompetenciaRepository repository;

    public OrcamentoCompetencia validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Orçamento por competência não encontrado com ID: " + id));
    }

    public OrcamentoCompetencia validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

