package com.upsaude.service.support.catalogoprocedimentos;

import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogoProcedimentosDomainService {

    public void validarPodeInativar(CatalogoProcedimentos procedimento) {
        if (Boolean.FALSE.equals(procedimento.getActive())) {
            log.warn("Tentativa de inativar procedimento já inativo. ID: {}", procedimento.getId());
            throw new BadRequestException("Procedimento já está inativo");
        }
    }
}

