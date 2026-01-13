package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CentroCustoValidationService {

    private final CentroCustoRepository repository;

    public void validarUnicidadeParaCriacao(CentroCustoRequest request, UUID tenantId) {
        validarCodigoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CentroCustoRequest request, UUID tenantId) {
        validarCodigoUnico(id, request, tenantId);
    }

    private void validarCodigoUnico(UUID id, CentroCustoRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) {
            return;
        }
        repository.findByCodigoAndTenant(request.getCodigo().trim(), tenantId)
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe um centro de custo com o código informado: " + request.getCodigo());
                    }
                });
    }
}

