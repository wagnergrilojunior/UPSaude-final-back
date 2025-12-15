package com.upsaude.service.support.procedimentosodontologicos;

import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcedimentosOdontologicosDomainService {

    public void validarPodeInativar(ProcedimentosOdontologicos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar procedimento odontológico já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Procedimento odontológico já está inativo");
        }
    }
}

