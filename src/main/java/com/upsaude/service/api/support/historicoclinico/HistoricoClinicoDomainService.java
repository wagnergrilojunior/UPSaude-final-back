package com.upsaude.service.api.support.historicoclinico;

import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HistoricoClinicoDomainService {

    public void validarPodeInativar(HistoricoClinico entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar histórico clínico já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Histórico clínico já está inativo");
        }
    }

    public void validarPodeDeletar(HistoricoClinico entity) {
        log.debug("Validando se histórico clínico pode ser deletado. ID: {}", entity.getId());

    }
}
