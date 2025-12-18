package com.upsaude.service.support.consultapuericultura;

import com.upsaude.api.request.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ConsultaPuericulturaValidationService {

    public void validarObrigatorios(ConsultaPuericulturaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da consulta de puericultura são obrigatórios");
        }
        if (request.getPuericultura() == null) {
            throw new BadRequestException("Puericultura é obrigatória");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getDataConsulta() == null) {
            throw new BadRequestException("Data da consulta é obrigatória");
        }
    }
}

