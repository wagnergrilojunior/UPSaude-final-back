package com.upsaude.service.support.fabricantesvacina;

import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FabricantesVacinaDomainService {

    public void validarPodeInativar(FabricantesVacina entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar fabricante de vacina já inativo. ID: {}", entity.getId());
            throw new BadRequestException("FabricantesVacina já está inativo");
        }
    }
}

