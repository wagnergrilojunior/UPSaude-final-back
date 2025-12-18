package com.upsaude.service.support.vacinacoes;

import com.upsaude.entity.vacina.Vacinacoes;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VacinacoesDomainService {

    public void validarPodeInativar(Vacinacoes entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar vacinação já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Vacinação já está inativa");
        }
    }
}

