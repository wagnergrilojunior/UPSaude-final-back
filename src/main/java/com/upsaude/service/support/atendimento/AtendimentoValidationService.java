package com.upsaude.service.support.atendimento;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AtendimentoValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do atendimento é obrigatório");
        }
    }

    public void validarObrigatorios(AtendimentoRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do atendimento são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }

        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional de saúde é obrigatório");
        }
    }
}
