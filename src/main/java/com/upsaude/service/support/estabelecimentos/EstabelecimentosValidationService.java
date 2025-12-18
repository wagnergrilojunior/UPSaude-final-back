package com.upsaude.service.support.estabelecimentos;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstabelecimentosValidationService {

    private final EstabelecimentosRepository repository;

    public void validarObrigatorios(EstabelecimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do estabelecimento é obrigatório");
        }
        if (request.getTipo() == null) {
            throw new BadRequestException("Tipo de estabelecimento é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(EstabelecimentosRequest request, UUID tenantId) {
        validarCnpjUnico(null, request, tenantId);
        validarCnesUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, EstabelecimentosRequest request, UUID tenantId) {
        validarCnpjUnico(id, request, tenantId);
        validarCnesUnico(id, request, tenantId);
    }

    private void validarCnpjUnico(UUID id, EstabelecimentosRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCnpj())) return;
        String cnpj = request.getCnpj().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCnpjAndTenantId(cnpj, tenantId)
            : repository.existsByCnpjAndTenantIdAndIdNot(cnpj, tenantId, id);

        if (duplicado) {
            throw new ConflictException("Já existe um estabelecimento cadastrado com o CNPJ informado: " + request.getCnpj());
        }
    }

    private void validarCnesUnico(UUID id, EstabelecimentosRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigoCnes())) return;
        String codigoCnes = request.getCodigoCnes().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCodigoCnesAndTenantId(codigoCnes, tenantId)
            : repository.existsByCodigoCnesAndTenantIdAndIdNot(codigoCnes, tenantId, id);

        if (duplicado) {
            throw new ConflictException("Já existe um estabelecimento cadastrado com o código CNES informado: " + request.getCodigoCnes());
        }
    }
}

