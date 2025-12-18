package com.upsaude.service.support.convenio;

import com.upsaude.entity.convenio.Convenio;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConvenioDomainService {

    public void validarPodeInativar(Convenio convenio) {
        if (Boolean.FALSE.equals(convenio.getActive())) {
            log.warn("Tentativa de inativar convênio já inativo. ID: {}", convenio.getId());
            throw new BadRequestException("Convênio já está inativo");
        }
    }
}

