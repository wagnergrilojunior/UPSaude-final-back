package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.profissional.medicao.MedicaoClinicaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MedicaoClinicaValidationService {

    public void validarObrigatorios(MedicaoClinicaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da medição clínica são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora são obrigatórias");
        }
    }
}
