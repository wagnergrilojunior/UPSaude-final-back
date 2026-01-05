package com.upsaude.service.api.support.plantao;

import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlantaoDomainService {

    public void validarPodeInativar(Plantao entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar plantão já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Plantão já está inativo");
        }
    }

    public void validarPodeDeletar(Plantao entity) {
        log.debug("Validando se plantão pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

