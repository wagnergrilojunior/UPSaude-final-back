package com.upsaude.service.support.paciente;

import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PacienteDomainService {

    public void validarPodeInativar(Paciente paciente) {
        if (Boolean.FALSE.equals(paciente.getActive())) {
            log.warn("Tentativa de inativar paciente já inativo. ID: {}", paciente.getId());
            throw new BadRequestException("Paciente já está inativo");
        }
    }

    public void validarPodeDeletar(Paciente paciente) {
        log.debug("Validando se paciente pode ser deletado. ID: {}", paciente.getId());
    }
}
