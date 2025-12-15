package com.upsaude.service.support.cidades;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CidadesValidationService {

    public void validarObrigatorios(CidadesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da cidade são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da cidade é obrigatório");
        }
        if (request.getEstado() == null) {
            throw new BadRequestException("Estado é obrigatório");
        }
    }
}

