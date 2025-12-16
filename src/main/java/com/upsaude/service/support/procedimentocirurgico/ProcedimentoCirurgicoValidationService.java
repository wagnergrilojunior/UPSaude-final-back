package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ProcedimentoCirurgicoValidationService {

    public void validarObrigatorios(ProcedimentoCirurgicoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do procedimento cirúrgico são obrigatórios");
        }
        if (request.getCirurgia() == null) {
            throw new BadRequestException("Cirurgia é obrigatória");
        }
        if (request.getDescricao() == null || request.getDescricao().isBlank()) {
            throw new BadRequestException("Descrição do procedimento é obrigatória");
        }
        if (request.getQuantidade() == null || request.getQuantidade() < 1) {
            throw new BadRequestException("Quantidade deve ser no mínimo 1");
        }
    }
}

