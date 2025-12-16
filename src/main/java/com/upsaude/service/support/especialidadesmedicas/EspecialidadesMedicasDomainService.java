package com.upsaude.service.support.especialidadesmedicas;

import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EspecialidadesMedicasDomainService {

    public void validarPodeInativar(EspecialidadesMedicas entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar especialidade médica já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Especialidade médica já está inativa");
        }
    }
}

