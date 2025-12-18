package com.upsaude.service.support.convenio;

import com.upsaude.entity.convenio.Convenio;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConvenioDomainService {

    public void validarPodeInativar(Convenio entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar convênio já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Convênio já está inativo");
        }
    }
}
