package com.upsaude.service.support.falta;

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class FaltaValidationService {

    public void validarObrigatorios(FaltaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da falta são obrigatórios");
        }
        if (request.getProfissional() == null && request.getMedico() == null) {
            throw new BadRequestException("Profissional ou médico é obrigatório");
        }
        if (request.getDataFalta() == null) {
            throw new BadRequestException("Data da falta é obrigatória");
        }
        if (request.getTipoFalta() == null) {
            throw new BadRequestException("Tipo de falta é obrigatório");
        }
    }
}
