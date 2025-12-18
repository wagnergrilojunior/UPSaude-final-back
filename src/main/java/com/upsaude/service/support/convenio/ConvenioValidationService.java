package com.upsaude.service.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.convenio.ConvenioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConvenioValidationService {

    private final ConvenioRepository repository;

    public void validarObrigatorios(ConvenioRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do convênio são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do convênio é obrigatório");
        }
        if (request.getTipo() == null) {
            throw new BadRequestException("Tipo de convênio é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(ConvenioRequest request, UUID tenantId) {
        validarCnpjUnico(null, request, tenantId);
        validarCodigoUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ConvenioRequest request, UUID tenantId) {
        validarCnpjUnico(id, request, tenantId);
        validarCodigoUnico(id, request, tenantId);
    }

    private void validarCnpjUnico(UUID id, ConvenioRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCnpj())) return;
        String cnpj = request.getCnpj().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCnpjAndTenantId(cnpj, tenantId)
            : repository.existsByCnpjAndTenantIdAndIdNot(cnpj, tenantId, id);

        if (duplicado) {
            throw new ConflictException("Já existe um convênio cadastrado com o CNPJ informado: " + request.getCnpj());
        }
    }

    private void validarCodigoUnico(UUID id, ConvenioRequest request, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) return;
        String codigo = request.getCodigo().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCodigoAndTenantId(codigo, tenantId)
            : repository.existsByCodigoAndTenantIdAndIdNot(codigo, tenantId, id);

        if (duplicado) {
            throw new ConflictException("Já existe um convênio cadastrado com o código informado: " + request.getCodigo());
        }
    }
}
