package com.upsaude.service.support.fabricantesmedicamento;

import org.springframework.stereotype.Service;

import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FabricantesMedicamentoDomainService {

    public void validarPodeInativar(FabricantesMedicamento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar fabricante de medicamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("FabricantesMedicamento já está inativo");
        }
    }
}

