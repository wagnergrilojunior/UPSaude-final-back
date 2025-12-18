package com.upsaude.service.support.prontuarios;

import com.upsaude.entity.prontuario.Prontuarios;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProntuariosDomainService {

    public void validarPodeInativar(Prontuarios entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar prontuário já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Prontuário já está inativo");
        }
    }
}

