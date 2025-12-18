package com.upsaude.service.support.consultaprenatal;

import com.upsaude.api.request.clinica.atendimento.ConsultaPreNatalRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ConsultaPreNatalValidationService {

    public void validarObrigatorios(ConsultaPreNatalRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da consulta pré-natal são obrigatórios");
        }
        if (request.getPreNatal() == null) {
            throw new BadRequestException("Pré-natal é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getDataConsulta() == null) {
            throw new BadRequestException("Data da consulta é obrigatória");
        }
    }
}

