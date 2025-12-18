package com.upsaude.service.support.estados;

import com.upsaude.api.request.geografico.EstadosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EstadosValidationService {

    public void validarObrigatorios(EstadosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estado são obrigatórios");
        }
        if (!StringUtils.hasText(request.getSigla())) {
            throw new BadRequestException("Sigla do estado é obrigatória");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do estado é obrigatório");
        }
    }
}

