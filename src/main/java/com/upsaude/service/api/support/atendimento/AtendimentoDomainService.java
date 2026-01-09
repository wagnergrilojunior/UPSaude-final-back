package com.upsaude.service.api.support.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtendimentoDomainService {

    public void aplicarDefaults(Atendimento entity) {

    }

    public void validarPodeInativar(Atendimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar atendimento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Atendimento já está inativo");
        }
    }

    public void validarPodeDeletar(Atendimento entity) {
        log.debug("Validando se atendimento pode ser deletado. ID: {}", entity.getId());

    }
}
