package com.upsaude.service.support.catalogoexames;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.CatalogoExamesRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class CatalogoExamesValidationService {

    public void validarObrigatorios(CatalogoExamesRequest request) {
        if (request == null) {
            throw new BadRequestException("Payload de catálogo de exames é obrigatório");
        }
        if (request.getTipoExame() == null) {
            throw new BadRequestException("Tipo de exame é obrigatório");
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
    }

    public void validarUnicidadeParaCriacao(CatalogoExamesRequest request, CatalogoExamesRepository repository, UUID tenantId) {
        validarNomeUnico(null, request, repository, tenantId);
        validarCodigoUnico(null, request, repository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CatalogoExamesRequest request, CatalogoExamesRepository repository, UUID tenantId) {
        validarNomeUnico(id, request, repository, tenantId);
        validarCodigoUnico(id, request, repository, tenantId);
    }

    private void validarNomeUnico(UUID id, CatalogoExamesRequest request, CatalogoExamesRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getNome())) return;
        String nome = request.getNome().trim();

        boolean duplicado = (id == null)
                ? repository.existsByNomeAndTenantId(nome, tenantId)
                : repository.existsByNomeAndTenantIdAndIdNot(nome, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                    "Já existe um exame cadastrado no catálogo com o nome '%s' no banco de dados", request.getNome()));
        }
    }

    private void validarCodigoUnico(UUID id, CatalogoExamesRequest request, CatalogoExamesRepository repository, UUID tenantId) {
        if (request == null || !StringUtils.hasText(request.getCodigo())) return;
        String codigo = request.getCodigo().trim();

        boolean duplicado = (id == null)
                ? repository.existsByCodigoAndTenantId(codigo, tenantId)
                : repository.existsByCodigoAndTenantIdAndIdNot(codigo, tenantId, id);

        if (duplicado) {
            throw new BadRequestException(String.format(
                    "Já existe um exame cadastrado no catálogo com o código '%s' no banco de dados", request.getCodigo()));
        }
    }
}
