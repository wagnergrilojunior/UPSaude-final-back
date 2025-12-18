package com.upsaude.service.support.estados;

import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstadosDomainService {

    public void validarPodeInativar(Estados entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar estado já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Estado já está inativo");
        }
    }
}

