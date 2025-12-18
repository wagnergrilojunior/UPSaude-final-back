package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CuidadosEnfermagemValidationService {

    public void validarObrigatorios(CuidadosEnfermagemRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do cuidado de enfermagem são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getTipoCuidado() == null) {
            throw new BadRequestException("Tipo de cuidado é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora são obrigatórios");
        }
    }
}
