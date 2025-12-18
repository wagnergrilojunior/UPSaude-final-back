package com.upsaude.service.support.permissoes;

import com.upsaude.api.request.sistema.PermissoesRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PermissoesValidationService {

    public void validarObrigatorios(PermissoesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da permissão são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da permissão é obrigatório");
        }
        if (!StringUtils.hasText(request.getModulo())) {
            throw new BadRequestException("Módulo da permissão é obrigatório");
        }
    }
}

