package com.upsaude.service.api.support.integracaogov;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class IntegracaoGovValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID é obrigatório");
        }
    }

    public void validarPacienteId(UUID pacienteId) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }

    public void validarObrigatorios(IntegracaoGovRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados da integração gov são obrigatórios");
        }
        validarPacienteId(request.getPaciente());
    }
}
