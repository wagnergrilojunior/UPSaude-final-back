package com.upsaude.service.api.support.escalatrabalho;

import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EscalaTrabalhoDomainService {

    public void validarPodeInativar(EscalaTrabalho entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar escala de trabalho já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Escala de trabalho já está inativa");
        }
    }

    public void validarPodeDeletar(EscalaTrabalho entity) {
        log.debug("Validando se escala de trabalho pode ser deletada. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

