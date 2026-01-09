package com.upsaude.service.api.support.consultas;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ConsultasValidationService {

    public void validarObrigatorios(ConsultaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da consulta são obrigatórios");
        }
        if (request.getAtendimento() == null) {
            throw new BadRequestException("Atendimento é obrigatório");
        }
        if (request.getMedico() == null) {
            throw new BadRequestException("Médico é obrigatório");
        }
    }
}
