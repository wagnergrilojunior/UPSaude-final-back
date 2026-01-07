package com.upsaude.service.api.support.agendamento;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AgendamentoValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }
    }

    public void validarObrigatorios(AgendamentoRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do agendamento são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }

        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora são obrigatórias");
        }

        if (request.getStatus() == null) {
            throw new BadRequestException("Status do agendamento é obrigatório");
        }
    }
}

