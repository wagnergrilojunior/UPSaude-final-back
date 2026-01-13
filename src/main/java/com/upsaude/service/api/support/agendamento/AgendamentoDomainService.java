package com.upsaude.service.api.support.agendamento;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoDomainService {

    private final AgendamentoStatusValidator statusValidator;

    public void aplicarDefaults(Agendamento entity) {
        if (entity.getEhEncaixe() == null) {
            entity.setEhEncaixe(false);
        }
        if (entity.getEhRetorno() == null) {
            entity.setEhRetorno(false);
        }
    }

    public void validarTransicaoStatus(Agendamento entity, StatusAgendamentoEnum novoStatus) {
        statusValidator.validarTransicao(entity, novoStatus);
    }

    public void validarPodeInativar(Agendamento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar agendamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Agendamento já está inativo");
        }
    }

    public void validarPodeDeletar(Agendamento entity) {
        log.debug("Validando se agendamento pode ser deletado. ID: {}", entity.getId());
    }
}

