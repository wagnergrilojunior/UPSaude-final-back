package com.upsaude.service.support.procedimentosodontologicos;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.ProcedimentosOdontologicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentosOdontologicosValidationService {

    private final ProcedimentosOdontologicosRepository repository;

    public void validarObrigatorios(ProcedimentosOdontologicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do procedimento odontológico são obrigatórios");
        }
        if (request.getCodigo() == null || request.getCodigo().isBlank()) {
            throw new BadRequestException("Código é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(ProcedimentosOdontologicosRequest request, UUID tenantId) {
        validarUnicidade(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ProcedimentosOdontologicosRequest request, UUID tenantId) {
        validarUnicidade(id, request, tenantId);
    }

    private void validarUnicidade(UUID id, ProcedimentosOdontologicosRequest request, UUID tenantId) {
        if (request == null || request.getCodigo() == null || request.getCodigo().isBlank()) return;

        boolean duplicado = (id == null)
            ? repository.existsByCodigoAndTenantId(request.getCodigo(), tenantId)
            : repository.existsByCodigoAndTenantIdAndIdNot(request.getCodigo(), tenantId, id);

        if (duplicado) {
            log.warn("Procedimento odontológico duplicado por código. codigo: {}, tenant: {}", request.getCodigo(), tenantId);
            throw new BadRequestException("Já existe um procedimento odontológico cadastrado com este código no tenant atual");
        }
    }
}

