package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanoContasValidationService {

    private final PlanoContasRepository repository;

    public void validarUnicidadeParaCriacao(PlanoContasRequest request, UUID tenantId) {
        validarNomeVersaoUnico(null, request, tenantId);
        validarPadraoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PlanoContasRequest request, UUID tenantId) {
        validarNomeVersaoUnico(id, request, tenantId);
        validarPadraoUnico(id, request, tenantId);
    }

    private void validarNomeVersaoUnico(UUID id, PlanoContasRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getNome()) || !StringUtils.hasText(request.getVersao())) {
            return;
        }

        repository.findByNomeAndVersaoAndTenant(request.getNome().trim(), request.getVersao().trim(), tenantId)
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe um plano de contas com o nome/versão informados");
                    }
                });
    }

    private void validarPadraoUnico(UUID id, PlanoContasRequest request, UUID tenantId) {
        if (request == null || !Boolean.TRUE.equals(request.getPadrao())) {
            return;
        }
        repository.findByPadrao(tenantId).ifPresent(existing -> {
            if (id == null || !existing.getId().equals(id)) {
                throw new ConflictException("Já existe um plano de contas padrão para este tenant");
            }
        });
    }
}

