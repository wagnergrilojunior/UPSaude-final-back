package com.upsaude.service.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.convenio.ConvenioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class ConvenioValidationService {

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
        if (request.getModalidade() == null) {
            throw new BadRequestException("Modalidade de convênio é obrigatória");
        }
    }

    public void validarUnicidadeParaCriacao(ConvenioRequest request, ConvenioRepository repository, UUID tenantId) {
        validarCnpjUnico(null, request, repository, tenantId);
        validarInscricaoEstadualUnica(null, request, repository, tenantId);
        validarCodigoUnico(null, request, repository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ConvenioRequest request, ConvenioRepository repository, UUID tenantId) {
        validarCnpjUnico(id, request, repository, tenantId);
        validarInscricaoEstadualUnica(id, request, repository, tenantId);
        validarCodigoUnico(id, request, repository, tenantId);
    }

    private void validarCnpjUnico(UUID id, ConvenioRequest request, ConvenioRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCnpj())) return;
        String cnpj = request.getCnpj().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCnpjAndTenantId(cnpj, tenantId)
            : repository.existsByCnpjAndTenantIdAndIdNot(cnpj, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                "Já existe um convênio cadastrado com o CNPJ '%s' no banco de dados", request.getCnpj()));
        }
    }

    private void validarInscricaoEstadualUnica(UUID id, ConvenioRequest request, ConvenioRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getInscricaoEstadual())) return;
        String ie = request.getInscricaoEstadual().trim();

        boolean duplicado = (id == null)
            ? repository.existsByInscricaoEstadualAndTenantId(ie, tenantId)
            : repository.existsByInscricaoEstadualAndTenantIdAndIdNot(ie, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                "Já existe um convênio cadastrado com a inscrição estadual '%s' no banco de dados", request.getInscricaoEstadual()));
        }
    }

    private void validarCodigoUnico(UUID id, ConvenioRequest request, ConvenioRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) return;
        String codigo = request.getCodigo().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCodigoAndTenantId(codigo, tenantId)
            : repository.existsByCodigoAndTenantIdAndIdNot(codigo, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                "Já existe um convênio cadastrado com o código '%s' no banco de dados", request.getCodigo()));
        }
    }
}

