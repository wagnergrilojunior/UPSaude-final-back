package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaFinanceiraValidationService {

    private final ContaFinanceiraRepository repository;

    public void validarUnicidadeParaCriacao(ContaFinanceiraRequest request, UUID tenantId) {
        validarNomeTipoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ContaFinanceiraRequest request, UUID tenantId) {
        validarNomeTipoUnico(id, request, tenantId);
    }

    private void validarNomeTipoUnico(UUID id, ContaFinanceiraRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getNome()) || !StringUtils.hasText(request.getTipo())) {
            return;
        }
        repository.findByNomeAndTipoAndTenant(request.getNome().trim(), request.getTipo().trim(), tenantId)
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe uma conta financeira com o nome/tipo informados");
                    }
                });
    }
}

