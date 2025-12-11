package com.upsaude.service.support.catalogoexames;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
}
