package com.upsaude.service.support.vacinas;

import org.springframework.stereotype.Service;

import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VacinasDomainService {

    public void validarPodeInativar(Vacinas vacina) {
        if (Boolean.FALSE.equals(vacina.getActive())) {
            log.warn("Tentativa de inativar vacina já inativa. ID: {}", vacina.getId());
            throw new BadRequestException("Vacina já está inativa");
        }
    }
}
