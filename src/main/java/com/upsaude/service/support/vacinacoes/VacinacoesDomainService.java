package com.upsaude.service.support.vacinacoes;

import org.springframework.stereotype.Service;

import com.upsaude.entity.saude_publica.vacina.Vacinacoes;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VacinacoesDomainService {

    public void validarPodeInativar(Vacinacoes entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar vacinação já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Vacinação já está inativa");
        }
    }
}

