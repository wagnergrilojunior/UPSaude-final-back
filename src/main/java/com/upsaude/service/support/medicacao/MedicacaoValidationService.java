package com.upsaude.service.support.medicacao;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MedicacaoValidationService {

    public void validarObrigatorios(MedicacaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da medicação são obrigatórios");
        }
    }
}

