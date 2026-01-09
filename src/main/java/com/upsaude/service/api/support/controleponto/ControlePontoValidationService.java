package com.upsaude.service.api.support.controleponto;

import com.upsaude.api.request.profissional.equipe.ControlePontoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ControlePontoValidationService {

    public void validarObrigatorios(ControlePontoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do controle de ponto são obrigatórios");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getTipoPonto() == null) {
            throw new BadRequestException("Tipo de ponto é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora são obrigatórios");
        }
    }
}
