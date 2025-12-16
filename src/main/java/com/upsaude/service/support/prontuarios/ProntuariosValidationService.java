package com.upsaude.service.support.prontuarios;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProntuariosValidationService {

    public void validarObrigatorios(ProntuariosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do prontuário são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }
}

