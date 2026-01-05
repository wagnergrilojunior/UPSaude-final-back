package com.upsaude.service.api.support.controleponto;

import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ControlePontoDomainService {

    public void validarPodeInativar(ControlePonto entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar controle de ponto já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Controle de ponto já está inativo");
        }
    }

    public void validarPodeDeletar(ControlePonto entity) {
        log.debug("Validando se controle de ponto pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

