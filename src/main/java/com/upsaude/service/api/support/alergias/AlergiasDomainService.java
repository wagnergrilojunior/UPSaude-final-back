package com.upsaude.service.api.support.alergias;

import com.upsaude.entity.paciente.alergia.Alergias;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlergiasDomainService {

    public void validarPodeInativar(Alergias alergia) {
        if (Boolean.FALSE.equals(alergia.getActive())) {
            log.warn("Tentativa de inativar alergia já inativa. ID: {}", alergia.getId());
            throw new BadRequestException("Alergia já está inativa");
        }
    }
}
