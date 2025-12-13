package com.upsaude.service.support.prenatal;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PreNatalValidationService {

    public void validarObrigatorios(PreNatalRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do pré-natal são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }
}

