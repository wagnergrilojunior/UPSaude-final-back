package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialValidationService {

    private final GuiaAtendimentoAmbulatorialRepository repository;

    public void validarUnicidadeParaCriacao(GuiaAtendimentoAmbulatorialRequest request, UUID tenantId) {
        validarNumeroUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, GuiaAtendimentoAmbulatorialRequest request, UUID tenantId) {
        validarNumeroUnico(id, request, tenantId);
    }

    private void validarNumeroUnico(UUID id, GuiaAtendimentoAmbulatorialRequest request, UUID tenantId) {
        if (request == null || request.getCompetencia() == null || !StringUtils.hasText(request.getNumero())) {
            return;
        }
        repository.findByNumeroAndTenantAndCompetencia(request.getNumero().trim(), tenantId, request.getCompetencia())
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe uma guia com este número para a competência informada");
                    }
                });
    }
}

