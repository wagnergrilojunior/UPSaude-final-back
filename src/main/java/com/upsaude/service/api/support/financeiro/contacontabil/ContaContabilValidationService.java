package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaContabilValidationService {

    private final ContaContabilRepository repository;

    public void validarUnicidadeParaCriacao(ContaContabilRequest request, UUID tenantId) {
        validarCodigoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ContaContabilRequest request, UUID tenantId) {
        validarCodigoUnico(id, request, tenantId);
    }

    private void validarCodigoUnico(UUID id, ContaContabilRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo()) || request.getPlanoContas() == null) {
            return;
        }
        repository.findByCodigoAndPlanoContas(request.getCodigo().trim(), request.getPlanoContas(), tenantId)
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe uma conta contábil com o código informado no plano de contas");
                    }
                });
    }
}

