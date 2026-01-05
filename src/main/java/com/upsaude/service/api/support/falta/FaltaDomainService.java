package com.upsaude.service.api.support.falta;

import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FaltaDomainService {

    public void validarPodeInativar(Falta entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar falta já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Falta já está inativa");
        }
    }

    public void validarPodeDeletar(Falta entity) {
        log.debug("Validando se falta pode ser deletada. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

