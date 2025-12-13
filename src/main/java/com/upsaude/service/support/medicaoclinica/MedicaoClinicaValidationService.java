package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MedicaoClinicaValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }
    }

    public void validarObrigatorios(MedicaoClinicaRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados da medição clínica são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }

        if (request.getDataHora() == null) {
            throw new BadRequestException("Data/hora é obrigatória");
        }
    }

    public void validarPacienteId(UUID pacienteId) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }
}
