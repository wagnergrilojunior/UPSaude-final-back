package com.upsaude.service.support.puericultura;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PuericulturaValidationService {

    public void validarObrigatorios(PuericulturaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da puericultura são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }
}

