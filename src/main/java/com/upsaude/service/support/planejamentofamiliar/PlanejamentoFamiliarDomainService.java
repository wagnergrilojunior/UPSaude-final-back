package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanejamentoFamiliarDomainService {

    public void aplicarDefaultsNaCriacao(PlanejamentoFamiliar entity) {
        if (entity.getAcompanhamentoAtivo() == null) {
            entity.setAcompanhamentoAtivo(true);
        }
    }

    public void validarPodeInativar(PlanejamentoFamiliar entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar planejamento familiar já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Planejamento familiar já está inativo");
        }
    }
}

