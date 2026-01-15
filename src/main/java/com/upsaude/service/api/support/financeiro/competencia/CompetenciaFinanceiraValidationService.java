package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraValidationService {

    private final CompetenciaFinanceiraRepository repository;

    public void validarUnicidadeParaCriacao(CompetenciaFinanceiraRequest request, UUID tenantId) {
        validarCodigoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CompetenciaFinanceiraRequest request, UUID tenantId) {
        validarCodigoUnico(id, request, tenantId);
    }

    private void validarCodigoUnico(UUID id, CompetenciaFinanceiraRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) {
            return;
        }
        String codigo = request.getCodigo().trim();

        boolean duplicado;
        if (id == null) {
            duplicado = repository.existsByCodigoAndTenant_Id(codigo, tenantId);
        } else {
            duplicado = repository.existsByCodigoAndTenant_IdAndIdNot(codigo, tenantId, id);
        }

        if (duplicado) {
            throw new ConflictException("Já existe uma competência financeira cadastrada com o código informado: " + codigo);
        }
    }
}

