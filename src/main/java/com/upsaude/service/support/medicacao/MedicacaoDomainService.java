package com.upsaude.service.support.medicacao;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicacaoDomainService {

    public void validarPodeInativar(Medicacao medicacao) {
        if (Boolean.FALSE.equals(medicacao.getActive())) {
            log.warn("Tentativa de inativar medicação já inativa. ID: {}", medicacao.getId());
            throw new BadRequestException("Medicação já está inativa");
        }
    }
}

