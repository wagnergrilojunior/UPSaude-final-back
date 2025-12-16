package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class TratamentosOdontologicosValidationService {

    public void validarObrigatorios(TratamentosOdontologicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do tratamento odontológico são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
            throw new BadRequestException("Título é obrigatório");
        }
    }
}

