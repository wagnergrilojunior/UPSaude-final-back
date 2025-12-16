package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TratamentosOdontologicosDomainService {

    public void validarPodeInativar(TratamentosOdontologicos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar tratamento odontológico já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Tratamento odontológico já está inativo");
        }
    }
}

