package com.upsaude.service.support.exames;

import com.upsaude.entity.Exames;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExamesDomainService {

    public void validarPodeInativar(Exames entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar exame já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Exame já está inativo");
        }
    }
}

