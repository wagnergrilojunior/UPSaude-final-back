package com.upsaude.service.support.departamentos;

import com.upsaude.entity.departamento.Departamentos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DepartamentosDomainService {

    public void validarPodeInativar(Departamentos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar departamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Departamento já está inativo");
        }
    }
}

