package com.upsaude.service.api.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
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

    public void validarUnicidadeParaCriacao(ConvenioRequest request, UUID tenantId) {
        validarCnpjUnico(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ConvenioRequest request, UUID tenantId) {
        validarCnpjUnico(id, request, tenantId);
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
}
