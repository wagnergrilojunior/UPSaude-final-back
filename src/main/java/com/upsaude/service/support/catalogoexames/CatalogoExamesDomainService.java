package com.upsaude.service.support.catalogoexames;

import com.upsaude.entity.exame.CatalogoExames;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogoExamesDomainService {

    public void validarPodeInativar(CatalogoExames exame) {
        if (Boolean.FALSE.equals(exame.getActive())) {
            log.warn("Tentativa de inativar exame já inativo. ID: {}", exame.getId());
            throw new BadRequestException("Exame já está inativo");
        }
    }
}
