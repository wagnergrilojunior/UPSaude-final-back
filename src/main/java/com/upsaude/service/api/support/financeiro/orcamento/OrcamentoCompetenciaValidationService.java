package com.upsaude.service.api.support.financeiro.orcamento;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrcamentoCompetenciaValidationService {

    private final OrcamentoCompetenciaRepository repository;

    public void validarUnicidadeParaCriacao(OrcamentoCompetenciaRequest request, UUID tenantId) {
        validarCompetenciaUnica(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, OrcamentoCompetenciaRequest request, UUID tenantId) {
        validarCompetenciaUnica(id, request, tenantId);
    }

    private void validarCompetenciaUnica(UUID id, OrcamentoCompetenciaRequest request, UUID tenantId) {
        if (request == null || request.getCompetencia() == null) {
            return;
        }
        repository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe orçamento cadastrado para esta competência/tenant");
                    }
                });
    }
}

