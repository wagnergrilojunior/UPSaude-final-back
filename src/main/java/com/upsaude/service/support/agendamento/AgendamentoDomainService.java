package com.upsaude.service.support.agendamento;

import com.upsaude.entity.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgendamentoDomainService {

    public void validarPodeInativar(Agendamento agendamento) {
        if (Boolean.FALSE.equals(agendamento.getActive())) {
            log.warn("Tentativa de inativar agendamento já inativo. ID: {}", agendamento.getId());
            throw new BadRequestException("Agendamento já está inativo");
        }
    }

    public void validarPodeCancelar(Agendamento agendamento) {
        if (agendamento.getStatus() == StatusAgendamentoEnum.CANCELADO) {
            throw new BadRequestException("Agendamento já está cancelado");
        }
    }

    public void validarPodeConfirmar(Agendamento agendamento) {
        if (agendamento.getStatus() == StatusAgendamentoEnum.CANCELADO) {
            throw new BadRequestException("Não é possível confirmar um agendamento cancelado");
        }
    }

    public void validarPodeReagendar(Agendamento agendamento) {
        if (agendamento.getStatus() == StatusAgendamentoEnum.CANCELADO) {
            throw new BadRequestException("Não é possível reagendar um agendamento cancelado");
        }
    }
}
