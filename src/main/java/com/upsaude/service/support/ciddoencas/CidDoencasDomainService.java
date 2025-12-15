package com.upsaude.service.support.ciddoencas;

import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CidDoencasDomainService {

    public void validarPodeInativar(CidDoencas entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar CID de doença já inativo. ID: {}", entity.getId());
            throw new BadRequestException("CID de doença já está inativo");
        }
    }
}

