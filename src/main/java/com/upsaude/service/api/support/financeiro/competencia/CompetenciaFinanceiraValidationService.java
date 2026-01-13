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

    public void validarUnicidadeParaCriacao(CompetenciaFinanceiraRequest request) {
        validarCodigoUnico(null, request);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CompetenciaFinanceiraRequest request) {
        validarCodigoUnico(id, request);
    }

    private void validarCodigoUnico(UUID id, CompetenciaFinanceiraRequest request) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) {
            return;
        }
        String codigo = request.getCodigo().trim();

        boolean duplicado = repository.findByCodigo(codigo)
                .filter(existing -> id == null || !existing.getId().equals(id))
                .isPresent();

        if (duplicado) {
            throw new ConflictException("Já existe uma competência financeira cadastrada com o código informado: " + codigo);
        }
    }
}

