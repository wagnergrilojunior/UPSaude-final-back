package com.upsaude.service.api.support.departamentos;

import com.upsaude.api.request.estabelecimento.departamento.DepartamentosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DepartamentosValidationService {

    public void validarObrigatorios(DepartamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do departamento são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do departamento é obrigatório");
        }
    }
}

