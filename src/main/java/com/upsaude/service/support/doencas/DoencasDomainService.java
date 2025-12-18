package com.upsaude.service.support.doencas;

import com.upsaude.entity.doencas.Doencas;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DoencasDomainService {

    public void validarPodeInativar(Doencas doenca) {
        if (Boolean.FALSE.equals(doenca.getActive())) {
            log.warn("Tentativa de inativar doença já inativa. ID: {}", doenca.getId());
            throw new BadRequestException("Doença já está inativa");
        }
    }
}

