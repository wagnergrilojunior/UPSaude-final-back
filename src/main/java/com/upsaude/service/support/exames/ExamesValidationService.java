package com.upsaude.service.support.exames;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.exame.ExamesRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ExamesValidationService {

    public void validarObrigatorios(ExamesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do exame são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }

    public void validarConsistenciaDatas(ExamesRequest request) {
        if (request == null) return;

        if (request.getDataExame() != null && request.getDataResultado() != null
            && request.getDataResultado().isBefore(request.getDataExame())) {
            throw new BadRequestException("Data do resultado não pode ser anterior à data do exame");
        }
    }
}

