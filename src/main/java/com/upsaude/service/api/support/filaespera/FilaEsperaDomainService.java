package com.upsaude.service.api.support.filaespera;

import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FilaEsperaDomainService {

    public void validarPodeInativar(FilaEspera entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar item da fila de espera já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Item da fila de espera já está inativo");
        }
    }

    public void validarPodeDeletar(FilaEspera entity) {
        log.debug("Validando se item da fila de espera pode ser deletado. ID: {}", entity.getId());

    }
}
