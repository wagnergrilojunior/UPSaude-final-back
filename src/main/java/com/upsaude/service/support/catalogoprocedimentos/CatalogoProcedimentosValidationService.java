package com.upsaude.service.support.catalogoprocedimentos;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class CatalogoProcedimentosValidationService {

    public void validarObrigatorios(CatalogoProcedimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Payload de catálogo de procedimentos é obrigatório");
        }
        if (request.getTipoProcedimento() == null) {
            throw new BadRequestException("Tipo de procedimento é obrigatório");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome é obrigatório");
        }
        if (request.getNome() != null && request.getNome().length() > 255) {
            throw new BadRequestException("Nome deve ter no máximo 255 caracteres");
        }
        if (request.getCodigo() != null && request.getCodigo().length() > 50) {
            throw new BadRequestException("Código deve ter no máximo 50 caracteres");
        }
        if (request.getProfissionalRequerido() != null && request.getProfissionalRequerido().length() > 100) {
            throw new BadRequestException("Profissional requerido deve ter no máximo 100 caracteres");
        }
    }

    public void validarUnicidadeParaCriacao(CatalogoProcedimentosRequest request, CatalogoProcedimentosRepository repository, UUID tenantId) {
        validarNomeUnico(null, request, repository, tenantId);
        validarCodigoUnico(null, request, repository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CatalogoProcedimentosRequest request, CatalogoProcedimentosRepository repository, UUID tenantId) {
        validarNomeUnico(id, request, repository, tenantId);
        validarCodigoUnico(id, request, repository, tenantId);
    }

    private void validarNomeUnico(UUID id, CatalogoProcedimentosRequest request, CatalogoProcedimentosRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getNome())) return;
        String nome = request.getNome().trim();

        boolean duplicado = (id == null)
            ? repository.existsByNomeAndTenantId(nome, tenantId)
            : repository.existsByNomeAndTenantIdAndIdNot(nome, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                "Já existe um procedimento cadastrado no catálogo com o nome '%s' no banco de dados", request.getNome()));
        }
    }

    private void validarCodigoUnico(UUID id, CatalogoProcedimentosRequest request, CatalogoProcedimentosRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) return;
        String codigo = request.getCodigo().trim();

        boolean duplicado = (id == null)
            ? repository.existsByCodigoAndTenantId(codigo, tenantId)
            : repository.existsByCodigoAndTenantIdAndIdNot(codigo, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                "Já existe um procedimento cadastrado no catálogo com o código '%s' no banco de dados", request.getCodigo()));
        }
    }
}

