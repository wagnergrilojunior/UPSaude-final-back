package com.upsaude.service.support.medico;

import com.upsaude.entity.profissional.Medicos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MedicoDomainService {

    public void validarPodeInativar(Medicos medico) {
        if (Boolean.FALSE.equals(medico.getActive())) {
            log.warn("Tentativa de inativar médico já inativo. ID: {}", medico.getId());
            throw new BadRequestException("Médico já está inativo");
        }
    }

    public void validarPodeDeletar(Medicos medico) {
        log.debug("Validando se médico pode ser deletado. ID: {}", medico.getId());
    }
}
