package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.clinica.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class DispensacoesMedicamentosValidationService {

    public void validarObrigatorios(DispensacoesMedicamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da dispensação de medicamento são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getMedicacao() == null) {
            throw new BadRequestException("Medicação é obrigatória");
        }
    }
}
