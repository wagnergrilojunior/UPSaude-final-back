package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.visita.VisitasDomiciliaresRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class VisitasDomiciliaresValidationService {

    public void validarObrigatorios(VisitasDomiciliaresRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da visita domiciliar são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getTipoVisita() == null) {
            throw new BadRequestException("Tipo de visita é obrigatório");
        }
        if (request.getDataVisita() == null) {
            throw new BadRequestException("Data da visita é obrigatória");
        }
    }
}

