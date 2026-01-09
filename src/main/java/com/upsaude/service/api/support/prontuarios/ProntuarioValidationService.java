package com.upsaude.service.api.support.prontuarios;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProntuarioValidationService {

    public void validarObrigatorios(ProntuarioRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do prontuário são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }
}

