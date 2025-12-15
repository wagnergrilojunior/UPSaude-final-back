package com.upsaude.service.support.falta;

import com.upsaude.api.request.FaltaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class FaltaValidationService {

    public void validarObrigatorios(FaltaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da falta são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional de saúde é obrigatório");
        }
        if (request.getDataFalta() == null) {
            throw new BadRequestException("Data da falta é obrigatória");
        }
        if (request.getTipoFalta() == null) {
            throw new BadRequestException("Tipo de falta é obrigatório");
        }
    }

    public void validarConsistenciaDatas(FaltaRequest request) {
        if (request == null) return;

        if (request.getDataInicio() != null && request.getDataFim() != null
            && request.getDataInicio().isAfter(request.getDataFim())) {
            throw new BadRequestException("Data início não pode ser maior que data fim");
        }

        if (request.getDataFalta() != null && request.getDataInicio() != null
            && request.getDataFalta().isBefore(request.getDataInicio())) {
            throw new BadRequestException("Data da falta não pode ser anterior à data início");
        }

        if (request.getDataFalta() != null && request.getDataFim() != null
            && request.getDataFalta().isAfter(request.getDataFim())) {
            throw new BadRequestException("Data da falta não pode ser posterior à data fim");
        }
    }
}

