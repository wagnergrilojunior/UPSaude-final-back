package com.upsaude.service.support.consultas;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.atendimento.ConsultasRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ConsultasValidationService {

    public void validarObrigatorios(ConsultasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da consulta são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }
}
