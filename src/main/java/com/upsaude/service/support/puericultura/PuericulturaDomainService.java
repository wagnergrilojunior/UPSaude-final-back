package com.upsaude.service.support.puericultura;

import com.upsaude.entity.puericultura.Puericultura;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PuericulturaDomainService {

    public void aplicarDefaults(Puericultura entity) {
        if (entity.getAcompanhamentoAtivo() == null) {
            entity.setAcompanhamentoAtivo(true);
        }
    }

    public void validarPodeInativar(Puericultura entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar puericultura já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Puericultura já está inativa");
        }
    }
}

