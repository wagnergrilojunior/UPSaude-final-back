package com.upsaude.service.api.support.checkinatendimento;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CheckInAtendimentoValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do check-in é obrigatório");
        }
    }

    public void validarObrigatorios(CheckInAtendimentoRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do check-in são obrigatórios");
        }

        if (request.getAgendamento() == null) {
            throw new BadRequestException("Agendamento é obrigatório");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getDataCheckin() == null) {
            throw new BadRequestException("Data do check-in é obrigatória");
        }
        if (request.getTipoCheckin() == null) {
            throw new BadRequestException("Tipo de check-in é obrigatório");
        }
    }
}
