package com.upsaude.service.api.support.equipesaude;

import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EquipeSaudeDomainService {

    public void validarPodeInativar(EquipeSaude equipe) {
        if (Boolean.FALSE.equals(equipe.getActive())) {
            log.warn("Tentativa de inativar equipe já inativa. ID: {}", equipe.getId());
            throw new BadRequestException("Equipe já está inativa");
        }
    }

    public void validarPodeDeletar(EquipeSaude equipe) {
        log.debug("Validando se equipe pode ser deletada. ID: {}", equipe.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}
