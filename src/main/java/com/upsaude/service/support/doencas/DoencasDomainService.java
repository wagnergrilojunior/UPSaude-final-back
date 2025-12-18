package com.upsaude.service.support.doencas;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.doencas.Doencas;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DoencasDomainService {

    public void validarPodeInativar(Doencas doenca) {
        if (Boolean.FALSE.equals(doenca.getActive())) {
            log.warn("Tentativa de inativar doença já inativa. ID: {}", doenca.getId());
            throw new BadRequestException("Doença já está inativa");
        }
    }
}

